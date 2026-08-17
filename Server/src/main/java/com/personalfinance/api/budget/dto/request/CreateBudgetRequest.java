package com.personalfinance.api.budget.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateBudgetRequest {

    @NotNull(message = "Amount is required!")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0!")
    private BigDecimal amount;

    @NotNull(message = "Category is required!")
    private Integer categoryId;
}
