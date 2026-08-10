package com.personalfinance.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank(message = "Current password is required!")
    private String currentPassword;

    @NotBlank(message = "New password is required!")
    @Size(min = 6, max = 50, message = "New password must be beetween 6 and 50 characters!")
    private String newPassword;

    private String confirmPassword;
}
