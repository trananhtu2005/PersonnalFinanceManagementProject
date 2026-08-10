package com.personalfinance.api.wallet.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWalletRequest {

    private String name;
    private BigDecimal balance;

    public boolean isEmpty() {
        return name == null
                && balance == null;
    }
}
