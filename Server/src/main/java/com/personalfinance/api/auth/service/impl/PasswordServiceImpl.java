package com.personalfinance.api.auth.service.impl;

import com.personalfinance.api.auth.service.PasswordService;
import com.personalfinance.api.user.entity.User;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void validatePassword(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword)) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void checkPassword(String password, User user) {
        String encodedPassword = user.getPassword();
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new AppException(ErrorCode.OLD_PASSWORD_INCORRECT);
        }
    }
}
