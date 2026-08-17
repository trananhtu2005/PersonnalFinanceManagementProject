package com.personalfinance.api.budget.dto.request;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBudgetRequest {

    @DecimalMin(value = "0.01", message = "Amount must be greater than 0!")
    private BigDecimal amount;

    public boolean isEmpty() {
        return amount == null;
    }
}
