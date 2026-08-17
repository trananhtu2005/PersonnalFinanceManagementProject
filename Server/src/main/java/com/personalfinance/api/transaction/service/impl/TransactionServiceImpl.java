package com.personalfinance.api.transaction.service.impl;

import com.personalfinance.api.budget.service.BudgetService;
import com.personalfinance.api.category.entity.Category;
import com.personalfinance.api.category.repository.CategoryRepository;
import com.personalfinance.api.transaction.dto.request.CreateTransactionRequest;
import com.personalfinance.api.transaction.dto.request.UpdateTransactionRequest;
import com.personalfinance.api.transaction.dto.response.TransactionResponse;
import com.personalfinance.api.transaction.entity.Transaction;
import com.personalfinance.api.transaction.repository.TransactionRepository;
import com.personalfinance.api.transaction.service.TransactionService;
import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.service.CurrentUserService;
import com.personalfinance.api.wallet.entity.Wallet;
import com.personalfinance.api.wallet.repository.WalletRepository;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import com.personalfinance.validator.DateInputValidator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final CategoryRepository categoryRepository;
    private final CurrentUserService currentUserService;
    private final BudgetService budgetService;
    private final DateInputValidator dateInputValidator;

    @Override
    public Page<TransactionResponse> getAllTransactions(Integer month, Integer year, Integer categoryId, Pageable pageable) {
        User user = currentUserService.getCurrentUser();
        LocalDate now = LocalDate.now();

        if (month == null) {
            month = now.getMonthValue();
        }
        if (year == null) {
            year = now.getYear();
        }

        dateInputValidator.isMonthAndYearValid(month, year);

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startDate = yearMonth
                .atDay(1)
                .atStartOfDay();
        LocalDateTime endDate = yearMonth
                .plusMonths(1)
                .atDay(1)
                .atStartOfDay();

        return transactionRepository.findTransactions(user, startDate, endDate, categoryId, pageable)
                .map(t -> TransactionResponse.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .note(t.getNote())
                .date(t.getDate())
                .walletName(t.getWallet().getName())
                .categoryName(t.getCategory().getName())
                .build()
                );
    }

    @Override
    @Transactional
    public void createTransaction(CreateTransactionRequest request) {
        User user = currentUserService.getCurrentUser();
        Wallet wallet = walletRepository.findByIdAndUserAndDeletedFalse(request.getWalletId(), user)
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
        Category category = categoryRepository.findByIdAndUserAndDeletedFalse(request.getCategoryId(), user)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .note(request.getNote())
                .date(request.getDate())
                .user(user)
                .wallet(wallet)
                .category(category)
                .build();
        transactionRepository.save(transaction);
        YearMonth yearMonth = YearMonth.from(request.getDate());
        budgetService.updateSpent(user, category, yearMonth);
    }

    @Override
    public void updateTransaction(Integer id, UpdateTransactionRequest request) {
        if (request.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA_TO_UPDATE);
        }

        User user = currentUserService.getCurrentUser();
        Transaction transaction = transactionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        Category oldCategory = transaction.getCategory();
        YearMonth oldYearMonth = YearMonth.from(transaction.getDate());
        
        if (request.getWalletId() != null) {
            Wallet wallet = walletRepository.findByIdAndUserAndDeletedFalse(request.getWalletId(), user)
                    .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
            transaction.setWallet(wallet);
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByIdAndUserAndDeletedFalse(request.getCategoryId(), user)
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            transaction.setCategory(category);
        }
        if (request.getAmount() != null) {
            transaction.setAmount(request.getAmount());
        }
        if (request.getNote() != null) {
            transaction.setNote(request.getNote());
        }
        if (request.getDate() != null) {
            transaction.setDate(request.getDate());
        }

        transactionRepository.save(transaction);
        Category newCategory = transaction.getCategory();
        YearMonth newYearMonth = YearMonth.from(transaction.getDate());
        budgetService.updateSpent(user, oldCategory, oldYearMonth);
        
        if(!oldCategory.getId().equals(newCategory.getId())
                || !oldYearMonth.equals(newYearMonth)) {
            budgetService.updateSpent(user, newCategory, newYearMonth);
        }
    }

    @Override
    public void deleteTransaction(Integer id) {
        User user = currentUserService.getCurrentUser();
        Transaction transaction = transactionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        Category category = transaction.getCategory();
        YearMonth yearMonth = YearMonth.from(transaction.getDate());
        transactionRepository.delete(transaction);
        budgetService.updateSpent(user, category, yearMonth);
    }
}
