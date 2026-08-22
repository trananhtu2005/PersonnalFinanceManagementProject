package com.personalfinance.api.category.dto.request;

import com.personalfinance.api.category.entity.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {

    @NotBlank(message = "Name is required!")
    private String name;

    @NotNull(message = "Color is required!")
    private Integer colorId;

    @NotNull(message = "Type is required!")
    private CategoryType type;
}
