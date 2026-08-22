package com.personalfinance.api.transaction.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransactionRequest {

    @NotNull(message = "Amount is required!")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0!")
    private BigDecimal amount;

    private String note;

    @NotNull(message = "Date is required!")
    @PastOrPresent(message = "Time must be in the past or the present!")
    private LocalDateTime date;

    @NotNull(message = "Wallet is required!")
    private Integer walletId;

    @NotNull(message = "Category is required!")
    private Integer categoryId;
    
    private Integer savingGoalId;
}
