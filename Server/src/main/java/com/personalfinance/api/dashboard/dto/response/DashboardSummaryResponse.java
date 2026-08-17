package com.personalfinance.api.dashboard.dto.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardSummaryResponse {

    private final BigDecimal amount;
    private final BigDecimal difference;
}
