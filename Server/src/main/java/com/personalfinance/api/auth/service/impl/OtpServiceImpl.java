package com.personalfinance.api.auth.service.impl;

import com.personalfinance.api.auth.entity.Otp;
import com.personalfinance.api.auth.repository.OtpRepository;
import com.personalfinance.api.auth.service.OtpService;
import com.personalfinance.api.mail.service.EmailService;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final Random random = new Random();

    @Value("${otp.expiration}")
    private Long otpExpiration;

    @Value("${otp.resend-delay}")
    private Long resendDelay;

    private String generateOtp() {
        int otp = random.nextInt(1000000);

        return String.format("%06d", otp);
    }

    @Override
    public void sendForgetPasswordOtp(String email) {
        Otp latestOtp = otpRepository.findFirstByEmailOrderByCreatedAtDesc(email).orElse(null);

        if (latestOtp != null) {
            Duration duration = Duration.between(latestOtp.getCreatedAt(), LocalDateTime.now());

            if (duration.toMillis() < resendDelay) {
                throw new AppException(ErrorCode.OTP_REQUEST_TOO_FREQUENT);
            }
            if (!latestOtp.isUsed()) {
                latestOtp.setUsed(true);
                otpRepository.save(latestOtp);
            }
        }

        String code = generateOtp();
        Otp otp = Otp.builder()
                .email(email)
                .code(code)
                .createdAt(LocalDateTime.now())
                .expireAt(LocalDateTime.now().plus(Duration.ofMillis(otpExpiration)))
                .used(false)
                .build();

        otpRepository.save(otp);
        try {
            emailService.sendOtp(otp.getEmail(), otp.getCode());
        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_SENDER_FAILED);
        }
    }

    @Override
    public Otp getValidOtp(String email, String code) {
        Otp otp = otpRepository.findByEmailAndCode(email, code)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_OTP));

        if (otp.isUsed()) {
            throw new AppException(ErrorCode.OTP_ALREADY_USED);
        }
        if (otp.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }

        return otp;
    }

    @Override
    public void useOtp(Otp otp) {
        otp.setUsed(true);
        otpRepository.save(otp);
    }
}
