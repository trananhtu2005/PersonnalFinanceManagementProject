package com.personalfinance.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUsernameRequest {

    @NotBlank(message = "Username is required!")
    @Size(min = 6, max = 50, message = "Username must be beetween 6 and 50 characters!")
    private String username;
}
