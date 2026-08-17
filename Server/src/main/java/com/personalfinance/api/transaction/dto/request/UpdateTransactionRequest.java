package com.personalfinance.api.transaction.dto.request;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTransactionRequest {

    @DecimalMin(value = "0.01", message = "Amount must be greater than 0!")
    private BigDecimal amount;

    private String note;
    private LocalDateTime date;
    private Integer walletId;
    private Integer categoryId;

    public boolean isEmpty() {
        return amount == null
                && note == null
                && date == null
                && walletId == null
                && categoryId == null;
    }
}
