package com.personalfinance.api.admin.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminDashboardResponse {

    private final Long totalUsers;
    private final Long newUsersThisMonth;
    private final Long newUsersToday;
    private final List<MonthlyUserResponse> monthlyUserStats;
}
