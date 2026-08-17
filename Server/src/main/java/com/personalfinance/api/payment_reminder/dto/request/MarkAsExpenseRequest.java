package com.personalfinance.api.payment_reminder.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkAsExpenseRequest {

    @NotBlank(message = "Wallet is required!")
    private Integer walletId;
}
