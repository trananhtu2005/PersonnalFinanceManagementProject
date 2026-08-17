package com.personalfinance.api.transaction.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionResponse {

    private final Integer id;
    private final BigDecimal amount;
    private final String note;
    private final LocalDateTime date;
    private final String walletName;
    private final String categoryName;
}
