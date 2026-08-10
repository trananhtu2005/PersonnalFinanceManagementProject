package com.personalfinance.api.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateColorRequest {

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Code is required!")
    private String code;
}
