package com.personalfinance.api.mail.service.impl;

import com.personalfinance.api.mail.service.EmailService;
import com.personalfinance.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${otp.expiration}")
    private Long expiration;

    @Override
    public void sendOtp(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Personal Finance - OTP Verification");
        message.setText("""
                        Your OTP code is: %s
                        This code will expire in %d minutes.
                        """.formatted(otp, expiration / 60000));
        mailSender.send(message);
    }

    @Override
    public void sendWelcomeEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject("Personal Finance - Welcome New User");
        message.setText("""
                        Welcome to Personal Finance Management Website.
                        Your account has been created successfully.
                        Hope you have a good experience.
                        """);
        mailSender.send(message);
    }
}
