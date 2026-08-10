package com.personalfinance.api.wallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletRequest {

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Balance is required!")
    private BigDecimal balance;
}
