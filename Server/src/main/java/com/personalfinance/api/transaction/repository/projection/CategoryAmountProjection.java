package com.personalfinance.api.transaction.repository.projection;

import com.personalfinance.api.category.entity.CategoryType;
import java.math.BigDecimal;

public interface CategoryAmountProjection {

    Integer getCategoryId();

    String getCategoryName();

    String getColorCode();

    CategoryType getType();

    BigDecimal getAmount();
}
