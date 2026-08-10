package com.personalfinance.api.category.dto.response;

import com.personalfinance.api.category.entity.CategoryType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultCategoryResponse {

    private final Integer id;
    private final String name;
    private final CategoryType type;
}
