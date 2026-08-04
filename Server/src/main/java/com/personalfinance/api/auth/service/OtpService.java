package com.personalfinance.api.auth.service;

import com.personalfinance.api.auth.entity.Otp;

public interface OtpService {

    void sendForgetPasswordOtp(String email);

    Otp getValidOtp(String email, String code);
    
    void useOtp(Otp otp);
}
