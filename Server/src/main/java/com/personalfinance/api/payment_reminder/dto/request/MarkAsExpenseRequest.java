package com.personalfinance.api.payment_reminder.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkAsExpenseRequest {

    @NotNull(message = "Wallet is required!")
    private Integer walletId;
}
