package com.personalfinance.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Username is required!")
    @Size(min = 6, max = 50, message = "Username must be beetween 6 and 50 characters!")
    private String username;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, max = 50, message = "Password must be beetween 6 and 50 characters!")
    private String password;

    private String confirmPassword;
}
