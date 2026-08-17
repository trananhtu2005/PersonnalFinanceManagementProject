package com.personalfinance.api.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonthlyUserResponse {

    private final Integer month;
    private final Long count;
}
