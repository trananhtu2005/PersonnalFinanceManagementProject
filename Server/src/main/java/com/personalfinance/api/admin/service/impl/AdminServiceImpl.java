package com.personalfinance.api.admin.service.impl;

import com.personalfinance.api.admin.dto.response.AdminDashboardResponse;
import com.personalfinance.api.admin.dto.response.MonthlyUserResponse;
import com.personalfinance.api.admin.service.AdminService;
import com.personalfinance.api.user.repository.UserRepository;
import com.personalfinance.api.user.repository.projection.MonthlyUserProjection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public AdminDashboardResponse getDashboard() {
        long totalUsers = userRepository.count();
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime monthStart = currentMonth.atDay(1)
                .atStartOfDay();
        LocalDateTime monthEnd = currentMonth.plusMonths(1)
                .atDay(1)
                .atStartOfDay();
        long newUsersThisMonth = userRepository.countUsersByDateRange(monthStart, monthEnd);
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime tomorrowStart = today.plusDays(1)
                .atStartOfDay();
        long newUsersToday = userRepository.countUsersByDateRange(todayStart, tomorrowStart);
        LocalDateTime yearStart = LocalDate.of(currentMonth.getYear(), 1, 1)
                .atStartOfDay();
        LocalDateTime nextYearStart = LocalDate.of(currentMonth.getYear() + 1, 1, 1)
                .atStartOfDay();
        List<MonthlyUserProjection> results = userRepository.countUsersByMonth(yearStart, nextYearStart);
        Map<Integer, Long> userCountByMonth = results.stream()
                .collect(Collectors.toMap(MonthlyUserProjection::getMonth, MonthlyUserProjection::getCount));
        List<MonthlyUserResponse> monthlyUserStats = IntStream.rangeClosed(1, 12)
                .mapToObj(month -> MonthlyUserResponse.builder()
                .month(month)
                .count(userCountByMonth.getOrDefault(month, 0L))
                .build()
                ).toList();

        return AdminDashboardResponse.builder()
                .totalUsers(totalUsers)
                .newUsersThisMonth(newUsersThisMonth)
                .newUsersToday(newUsersToday)
                .monthlyUserStats(monthlyUserStats)
                .build();
    }
}
