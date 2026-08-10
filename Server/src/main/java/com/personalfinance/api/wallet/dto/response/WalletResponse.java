package com.personalfinance.api.wallet.dto.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WalletResponse {

    private final Integer id;
    private final String name;
    private final BigDecimal balance;
}
