package com.personalfinance.api.dashboard.service.impl;

import com.personalfinance.api.dashboard.dto.response.AnalysisResponse;
import com.personalfinance.api.dashboard.dto.response.DashboardResponse;
import com.personalfinance.api.dashboard.service.AiService;
import com.personalfinance.api.dashboard.service.AnalysisService;
import com.personalfinance.api.dashboard.service.DashboardService;
import com.personalfinance.api.transaction.dto.response.TransactionResponse;
import com.personalfinance.api.transaction.repository.TransactionRepository;
import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.service.CurrentUserService;
import com.personalfinance.validator.DateInputValidator;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    private final DashboardService dashboardService;
    private final TransactionRepository transactionRepository;
    private final CurrentUserService currentUserService;
    private final DateInputValidator dateInputValidator;
    private final AiService aiService;

    @Override
    public String analyze(Integer month, Integer year) {
        YearMonth current = YearMonth.now();

        if (month == null) {
            month = current.getMonthValue();
        }
        if (year == null) {
            year = current.getYear();
        }

        dateInputValidator.isMonthAndYearValid(month, year);
        DashboardResponse dashboard = dashboardService.getDashBoard(month, year);
        User user = currentUserService.getCurrentUser();
        YearMonth selected = YearMonth.of(year, month);
        var startDate = selected.atDay(1)
                .atStartOfDay();
        var endDate = selected.plusMonths(1)
                .atDay(1)
                .atStartOfDay();
        List<TransactionResponse> transactions = transactionRepository.findTransactions(user, startDate, endDate, null, Pageable.unpaged())
                .map(t -> TransactionResponse.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .note(t.getNote())
                .date(t.getDate())
                .walletName(t.getWallet().getName())
                .categoryName(t.getCategory().getName())
                .build()
                ).getContent();
        AnalysisResponse response = AnalysisResponse.builder()
                .dashboard(dashboard)
                .transactions(transactions)
                .build();

        return aiService.analyze(response);
    }
}
