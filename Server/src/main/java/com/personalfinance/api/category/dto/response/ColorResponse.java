package com.personalfinance.api.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColorResponse {

    private final Integer id;
    private final String name;
    private final String code;
}
