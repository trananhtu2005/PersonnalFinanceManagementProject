package com.personalfinance.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetPasswordRequest {

    @NotBlank(message = "Email is required!")
    private String email;
}
