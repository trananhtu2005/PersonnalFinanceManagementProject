package com.personalfinance.api.saving_goal.service.impl;

import com.personalfinance.api.category.entity.Category;
import com.personalfinance.api.category.entity.CategoryType;
import com.personalfinance.api.category.repository.CategoryRepository;
import com.personalfinance.api.notification.service.NotificationService;
import com.personalfinance.api.saving_goal.dto.request.CreateSavingGoalRequest;
import com.personalfinance.api.saving_goal.dto.request.DepositRequest;
import com.personalfinance.api.saving_goal.dto.request.UpdateSavingGoalRequest;
import com.personalfinance.api.saving_goal.dto.response.SavingGoalResponse;
import com.personalfinance.api.saving_goal.entity.SavingGoal;
import com.personalfinance.api.saving_goal.entity.SavingStatus;
import com.personalfinance.api.saving_goal.repository.SavingGoalRepository;
import com.personalfinance.api.saving_goal.service.SavingGoalService;
import com.personalfinance.api.transaction.entity.Transaction;
import com.personalfinance.api.transaction.repository.TransactionRepository;
import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.service.CurrentUserService;
import com.personalfinance.api.wallet.entity.Wallet;
import com.personalfinance.api.wallet.repository.WalletRepository;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SavingGoalServiceImpl implements SavingGoalService {

    private final SavingGoalRepository savingGoalRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final CurrentUserService currentUserService;
    private final NotificationService notificationService;

    @Override
    public Page<SavingGoalResponse> getAllSavingGoals(Pageable pageable) {
        User user = currentUserService.getCurrentUser();

        return savingGoalRepository.findByUser(user, pageable)
                .map(sg -> SavingGoalResponse.builder()
                .id(sg.getId())
                .title(sg.getTitle())
                .description(sg.getDescription())
                .target(sg.getTarget())
                .currentAmount(sg.getCurrentAmount())
                .startAt(sg.getStartAt())
                .endAt(sg.getEndAt())
                .status(sg.getStatus())
                .categoryName(sg.getCategory().getName())
                .build()
                );
    }

    @Override
    public void createSavingGoal(CreateSavingGoalRequest request) {
        User user = currentUserService.getCurrentUser();
        Category category = categoryRepository.findByIdAndUserAndDeletedFalse(request.getCategoryId(), user)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (category.getType() != CategoryType.SAVING) {
            throw new AppException(ErrorCode.INVALID_SAVING_CATEGORY);
        }

        SavingGoal savingGoal = SavingGoal.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .target(request.getTarget())
                .currentAmount(BigDecimal.ZERO)
                .endAt(request.getEndAt())
                .status(SavingStatus.IN_PROGRESS)
                .user(user)
                .build();
        savingGoal.setCategory(category);

        savingGoalRepository.save(savingGoal);
    }

    @Override
    public void updateSavingGoal(Integer id, UpdateSavingGoalRequest request) {
        if (request.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA_TO_UPDATE);
        }

        User user = currentUserService.getCurrentUser();
        SavingGoal savingGoal = savingGoalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AppException(ErrorCode.SAVING_GOAL_NOT_FOUND));

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByIdAndUserAndDeletedFalse(request.getCategoryId(), user)
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

            if (category.getType() != CategoryType.SAVING) {
                throw new AppException(ErrorCode.INVALID_SAVING_CATEGORY);
            }

            savingGoal.setCategory(category);
        }
        if (request.getTitle() != null) {
            savingGoal.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            savingGoal.setDescription(request.getDescription());
        }
        if (request.getTarget() != null) {
            savingGoal.setTarget(request.getTarget());

            if (savingGoal.getCurrentAmount().compareTo(request.getTarget()) >= 0) {
                savingGoal.setStatus(SavingStatus.COMPLETED);
            } else {
                savingGoal.setStatus(SavingStatus.IN_PROGRESS);
            }
        }
        if (request.getEndAt() != null) {
            savingGoal.setEndAt(request.getEndAt());
        }

        savingGoalRepository.save(savingGoal);
    }

    @Override
    public void deleteSavingGoal(Integer id) {
        User user = currentUserService.getCurrentUser();
        SavingGoal savingGoal = savingGoalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AppException(ErrorCode.SAVING_GOAL_NOT_FOUND));
        savingGoalRepository.delete(savingGoal);
    }

    @Override
    @Transactional
    public void deposit(Integer id, DepositRequest request) {
        User user = currentUserService.getCurrentUser();
        SavingGoal savingGoal = savingGoalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AppException(ErrorCode.SAVING_GOAL_NOT_FOUND));
        Wallet wallet = walletRepository.findByIdAndUserAndDeletedFalse(request.getWalletId(), user)
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .note(request.getNote())
                .date(LocalDateTime.now())
                .user(user)
                .wallet(wallet)
                .category(savingGoal.getCategory())
                .build();

        transactionRepository.save(transaction);
        savingGoal.setCurrentAmount(savingGoal.getCurrentAmount().add(request.getAmount()));

        if (savingGoal.getCurrentAmount().compareTo(savingGoal.getTarget()) >= 0) {
            savingGoal.setStatus(SavingStatus.COMPLETED);
            notificationService.createNotification("Congratulation!",
                    "You have a completed saving goal, visit your saving goal page to view it!",
                    user);
        }

        savingGoalRepository.save(savingGoal);
    }

    @Override
    public List<SavingGoalResponse> getInProgressSavingGoals() {
        User user = currentUserService.getCurrentUser();
        List<SavingGoal> savingGoals = savingGoalRepository.findByUserAndStatus(user, SavingStatus.IN_PROGRESS);

        return savingGoals.stream().map(sg
                -> SavingGoalResponse.builder()
                        .id(sg.getId())
                        .title(sg.getTitle())
                        .description(sg.getDescription())
                        .target(sg.getTarget())
                        .currentAmount(sg.getCurrentAmount())
                        .startAt(sg.getStartAt())
                        .endAt(sg.getEndAt())
                        .status(sg.getStatus())
                        .categoryName(sg.getCategory().getName())
                        .build()
        ).toList();
    }
}
