package com.personalfinance.api.budget.dto.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BudgetResponse {

    private final Integer id;
    private final BigDecimal amount;
    private final BigDecimal spent;
    private final boolean exceeded;
    private final Integer month;
    private final Integer year;
    private final String categoryName;
}
