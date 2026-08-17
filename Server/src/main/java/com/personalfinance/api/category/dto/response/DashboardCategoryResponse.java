package com.personalfinance.api.category.dto.response;

import com.personalfinance.api.category.entity.CategoryType;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardCategoryResponse {

    private final Integer id;
    private final String name;
    private final String colorCode;
    private final CategoryType type;
    private final BigDecimal amount;
}
