package com.personalfinance.api.category.dto.request;

import com.personalfinance.api.category.entity.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDefaultCategoryRequest {

    private String name;
    private CategoryType type;

    public boolean isEmpty() {
        return name == null
                && type == null;
    }
}
