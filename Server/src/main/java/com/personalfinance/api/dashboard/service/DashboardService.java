package com.personalfinance.api.dashboard.service;

import com.personalfinance.api.dashboard.dto.response.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashBoard(Integer month, Integer year);
}
