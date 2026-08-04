package com.personalfinance.api.mail.service;

import com.personalfinance.api.user.entity.User;

public interface EmailService {

    void sendOtp(String email, String otp);

    void sendWelcomeEmail(User user);
}
