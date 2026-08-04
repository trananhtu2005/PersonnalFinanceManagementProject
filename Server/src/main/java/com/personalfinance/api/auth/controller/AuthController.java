package com.personalfinance.api.auth.controller;

import com.personalfinance.api.auth.dto.request.ForgetPasswordRequest;
import com.personalfinance.api.auth.dto.request.LoginRequest;
import com.personalfinance.api.auth.dto.request.RefreshTokenRequest;
import com.personalfinance.api.auth.dto.request.RegisterRequest;
import com.personalfinance.api.auth.dto.request.ResetPasswordRequest;
import com.personalfinance.api.auth.dto.response.LoginResponse;
import com.personalfinance.api.auth.service.AuthService;
import com.personalfinance.common.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);

        MessageResponse response = MessageResponse
                .builder()
                .message("Register successfully!")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        LoginResponse response = authService.refreshToken(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request);

        MessageResponse response = MessageResponse
                .builder()
                .message("Logout successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<MessageResponse> forgetPassword(@Valid @RequestBody ForgetPasswordRequest request) {
        authService.forgetPassword(request);

        MessageResponse response = MessageResponse
                .builder()
                .message("OTP has been sent to your email!")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);

        MessageResponse response = MessageResponse
                .builder()
                .message("Password has been reset successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
