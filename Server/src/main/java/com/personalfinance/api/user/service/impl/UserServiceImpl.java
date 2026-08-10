package com.personalfinance.api.user.service.impl;

import com.personalfinance.api.auth.service.PasswordService;
import com.personalfinance.api.auth.service.RefreshTokenService;
import com.personalfinance.api.user.dto.request.ChangePasswordRequest;
import com.personalfinance.api.user.dto.request.ChangeUsernameRequest;
import com.personalfinance.api.user.dto.response.UserProfileResponse;
import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.entity.Username;
import com.personalfinance.api.user.repository.UserRepository;
import com.personalfinance.api.user.repository.UsernameRepository;
import com.personalfinance.api.user.service.CurrentUserService;
import com.personalfinance.api.user.service.UserService;
import com.personalfinance.api.user.service.UsernameService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsernameService usernameService;
    private final PasswordService passwordService;
    private final RefreshTokenService refreshTokenService;
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final UsernameRepository usernameRepository;

    @Override
    public UserProfileResponse getMyProfile() {
        User user = currentUserService.getCurrentUser();
        UserProfileResponse response = UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername().getUsername())
                .role(user.getRole())
                .build();

        return response;
    }

    @Override
    @Transactional
    public void changeUsername(ChangeUsernameRequest request) {
        User user = currentUserService.getCurrentUser();
        Username username = user.getUsername();
        usernameService.checkDuplicateUsername(request.getUsername());
        usernameService.checkUsernameChangeTime(username);
        username.setUsername(request.getUsername());
        username.setChangedAt(LocalDateTime.now());
        usernameRepository.save(username);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        User user = currentUserService.getCurrentUser();
        passwordService.checkPassword(request.getCurrentPassword(), user);
        passwordService.validatePassword(request.getNewPassword(), request.getConfirmPassword());
        user.setPassword(passwordService.encode(request.getNewPassword()));
        userRepository.save(user);
        refreshTokenService.revokeAll(user);
    }
}
