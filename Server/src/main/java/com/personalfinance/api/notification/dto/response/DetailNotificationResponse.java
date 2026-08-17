package com.personalfinance.api.notification.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetailNotificationResponse {

    private final Integer id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
}
