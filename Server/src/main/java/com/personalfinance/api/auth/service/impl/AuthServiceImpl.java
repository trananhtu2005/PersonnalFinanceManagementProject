package com.personalfinance.api.auth.service.impl;

import com.personalfinance.api.auth.dto.request.ForgetPasswordRequest;
import com.personalfinance.api.auth.dto.request.LoginRequest;
import com.personalfinance.api.auth.dto.request.RefreshTokenRequest;
import com.personalfinance.api.auth.dto.request.RegisterRequest;
import com.personalfinance.api.auth.dto.request.ResetPasswordRequest;
import com.personalfinance.api.auth.dto.response.LoginResponse;
import com.personalfinance.api.auth.entity.Otp;
import com.personalfinance.api.auth.entity.RefreshToken;
import com.personalfinance.api.auth.service.AuthService;
import com.personalfinance.api.auth.service.OtpService;
import com.personalfinance.api.auth.service.RefreshTokenService;
import com.personalfinance.api.mail.service.EmailService;
import com.personalfinance.api.user.entity.Role;
import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.entity.Username;
import com.personalfinance.api.user.repository.UserRepository;
import com.personalfinance.api.user.repository.UsernameRepository;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import com.personalfinance.security.jwt.JwtService;
import com.personalfinance.security.user.CustomUserDetails;
import com.personalfinance.validator.EmailValidator;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UsernameRepository usernameRepository;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;
    private final AuthenticationManager authenticationManager;

    private void validateEmail(String email) {
        if (!emailValidator.isEmail(email)) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }
    }

    private void validatePassword(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword)) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkDuplicateUsername(String username) {
        if (usernameRepository.existsByUsername(username)) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        validateEmail(request.getEmail());
        validatePassword(request.getPassword(), request.getConfirmPassword());
        checkDuplicateEmail(request.getEmail());
        checkDuplicateUsername(request.getUsername());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        Username username = new Username();
        username.setUsername(request.getUsername());
        username.setUser(user);

        usernameRepository.save(username);
        emailService.sendWelcomeEmail(user);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);
        LoginResponse response = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

        return response;
    }

    @Override
    @Transactional
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.getValidToken(request.getRefreshToken());
        User user = refreshToken.getUser();
        refreshTokenService.revoke(refreshToken);
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken newRefreshToken = refreshTokenService.create(user);
        LoginResponse response = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getRefreshToken())
                .build();

        return response;
    }

    @Override
    @Transactional
    public void logout(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.getValidToken(request.getRefreshToken());
        refreshTokenService.revoke(refreshToken);
    }

    @Override
    public void forgetPassword(ForgetPasswordRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
        }

        otpService.sendForgetPasswordOtp(request.getEmail());
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        validatePassword(request.getPassword(), request.getConfirmPassword());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        Otp otp = otpService.getValidOtp(request.getEmail(), request.getOtp());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        otpService.useOtp(otp);
        refreshTokenService.revokeAll(user);
    }
}
