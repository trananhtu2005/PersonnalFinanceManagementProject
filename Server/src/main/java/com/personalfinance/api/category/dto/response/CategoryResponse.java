package com.personalfinance.api.category.dto.response;

import com.personalfinance.api.category.entity.CategoryType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private final Integer id;
    private final String name;
    private final String colorCode;
    private final CategoryType type;
}
