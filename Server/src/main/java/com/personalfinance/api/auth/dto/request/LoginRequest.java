package com.personalfinance.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Email/Username is required!")
    private String login;

    @NotBlank(message = "Password is required!")
    private String password;
}
