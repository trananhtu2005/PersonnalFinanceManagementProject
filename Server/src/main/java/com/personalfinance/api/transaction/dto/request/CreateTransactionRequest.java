package com.personalfinance.api.transaction.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransactionRequest {

    @NotBlank(message = "Amount is required!")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0!")
    private BigDecimal amount;

    private String note;

    @NotBlank(message = "Date is required!")
    private LocalDateTime date;

    @NotBlank(message = "Wallet is required!")
    private Integer walletId;

    @NotBlank(message = "Category is required!")
    private Integer categoryId;
}
