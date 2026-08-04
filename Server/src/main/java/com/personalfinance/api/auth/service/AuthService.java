package com.personalfinance.api.auth.service;

import com.personalfinance.api.auth.dto.request.ForgetPasswordRequest;
import com.personalfinance.api.auth.dto.request.LoginRequest;
import com.personalfinance.api.auth.dto.request.RefreshTokenRequest;
import com.personalfinance.api.auth.dto.request.RegisterRequest;
import com.personalfinance.api.auth.dto.request.ResetPasswordRequest;
import com.personalfinance.api.auth.dto.response.LoginResponse;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);

    void forgetPassword(ForgetPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
