package com.personalfinance.api.saving_goal.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositRequest {

    @NotBlank(message = "Amount is required!")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0!")
    private BigDecimal amount;

    private String note;

    @NotBlank(message = "Wallet is required!")
    private Integer walletId;
}
