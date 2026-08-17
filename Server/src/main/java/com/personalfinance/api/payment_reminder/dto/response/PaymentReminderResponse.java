package com.personalfinance.api.payment_reminder.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentReminderResponse {

    private final Integer id;
    private final String title;
    private final BigDecimal amount;
    private final LocalDateTime dueAt;
    private final String note;
    private final String categoryName;
}
