package com.personalfinance.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String username;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    private String confirmPassword;
}
