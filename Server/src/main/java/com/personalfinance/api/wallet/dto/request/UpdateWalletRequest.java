package com.personalfinance.api.wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWalletRequest {

    private String name;

    @DecimalMin(value = "0.01", message = "Balance must be greater than 0!")
    private BigDecimal balance;

    public boolean isEmpty() {
        return name == null
                && balance == null;
    }
}
