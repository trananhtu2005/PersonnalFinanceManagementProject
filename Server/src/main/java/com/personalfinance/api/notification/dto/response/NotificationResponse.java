package com.personalfinance.api.notification.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {

    private final Integer id;
    private final String title;
    private final boolean read;
    private final LocalDateTime createdAt;
}
