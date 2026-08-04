package com.personalfinance.common;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiErrorResponse {

    private final LocalDateTime timestamp;
    private final Integer status;
    private final String code;
    private final String message;
}
