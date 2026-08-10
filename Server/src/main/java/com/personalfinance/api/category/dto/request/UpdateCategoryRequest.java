package com.personalfinance.api.category.dto.request;

import com.personalfinance.api.category.entity.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    private String name;
    private Integer colorId;
    private CategoryType type;

    public boolean isEmpty() {
        return name == null
                && colorId == null
                && type == null;
    }
}
