package com.personalfinance.api.category.dto.request;

import com.personalfinance.api.category.entity.CategoryType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Color is required!")
    private Integer colorId;

    @NotBlank(message = "Type is required!")
    private CategoryType type;
}
