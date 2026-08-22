package com.personalfinance.api.payment_reminder.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentReminderRequest {

    @NotBlank(message = "Title is required!")
    private String title;

    @NotNull(message = "Amount is required!")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0!")
    private BigDecimal amount;

    @NotNull(message = "Due time is required!")
    private LocalDateTime dueAt;

    private String note;

    @NotNull(message = "Category is required!")
    private Integer categoryId;
}
