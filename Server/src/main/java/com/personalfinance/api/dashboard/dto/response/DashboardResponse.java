package com.personalfinance.api.dashboard.dto.response;

import com.personalfinance.api.budget.dto.response.BudgetResponse;
import com.personalfinance.api.category.dto.response.DashboardCategoryResponse;
import com.personalfinance.api.payment_reminder.dto.response.PaymentReminderResponse;
import com.personalfinance.api.saving_goal.dto.response.SavingGoalResponse;
import com.personalfinance.api.wallet.dto.response.WalletResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardResponse {

    private final Integer month;
    private final Integer year;
    private final BigDecimal sumBalance;
    private final DashboardSummaryResponse expense;
    private final DashboardSummaryResponse income;
    private final DashboardSummaryResponse saving;
    private final List<DashboardCategoryResponse> categories;
    private final List<BudgetResponse> budgets;
    private final List<WalletResponse> wallets;
    private final List<PaymentReminderResponse> paymentReminders;
    private final List<SavingGoalResponse> savingGoals;
}
