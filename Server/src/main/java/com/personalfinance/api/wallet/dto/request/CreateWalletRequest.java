package com.personalfinance.api.wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletRequest {

    @NotBlank(message = "Name is required!")
    private String name;

    @NotNull(message = "Balance is required!")
    @DecimalMin(value = "0.01", message = "balance must be greater than 0!")
    private BigDecimal balance;
}
