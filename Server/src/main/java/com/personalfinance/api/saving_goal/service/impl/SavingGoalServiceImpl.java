package com.personalfinance.api.saving_goal.service.impl;

import com.personalfinance.api.saving_goal.dto.request.CreateSavingGoalRequest;
import com.personalfinance.api.saving_goal.dto.request.DepositRequest;
import com.personalfinance.api.saving_goal.dto.request.UpdateSavingGoalRequest;
import com.personalfinance.api.saving_goal.dto.response.SavingGoalResponse;
import com.personalfinance.api.saving_goal.entity.SavingGoal;
import com.personalfinance.api.saving_goal.entity.SavingStatus;
import com.personalfinance.api.saving_goal.repository.SavingGoalRepository;
import com.personalfinance.api.saving_goal.service.SavingGoalService;
import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.service.CurrentUserService;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingGoalServiceImpl implements SavingGoalService {

    private final SavingGoalRepository savingGoalRepository;
    private final CurrentUserService currentUserService;

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
                .build()
                );
    }

    @Override
    public void createSavingGoal(CreateSavingGoalRequest request) {
        User user = currentUserService.getCurrentUser();
        SavingGoal savingGoal = SavingGoal.builder()
                .title(request.getTitle())
                .target(request.getTarget())
                .currentAmount(BigDecimal.ZERO)
                .endAt(request.getEndAt())
                .status(SavingStatus.IN_PROGRESS)
                .user(user)
                .build();

        if (request.getDescription() != null) {
            savingGoal.setDescription(request.getDescription());
        }

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
    public void deposit(Integer id, DepositRequest request) {

    }
}
