package com.personalfinance.api.user.service.impl;

import com.personalfinance.api.user.entity.Username;
import com.personalfinance.api.user.repository.UsernameRepository;
import com.personalfinance.api.user.service.UsernameService;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsernameServiceImpl implements UsernameService {

    private final UsernameRepository usernameRepository;

    @Override
    public void checkDuplicateUsername(String username) {
        if (usernameRepository.existsByUsername(username)) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
    }

    @Override
    public void checkUsernameChangeTime(Username username) {
        if (username.getChangedAt().plusDays(7).isAfter(LocalDateTime.now())) {
            throw new AppException(ErrorCode.USERNAME_CHANGE_TOO_FREQUENT);
        }
    }
}
