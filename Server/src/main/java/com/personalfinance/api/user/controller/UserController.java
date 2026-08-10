package com.personalfinance.api.user.controller;

import com.personalfinance.api.user.dto.request.ChangePasswordRequest;
import com.personalfinance.api.user.dto.request.ChangeUsernameRequest;
import com.personalfinance.api.user.dto.response.UserProfileResponse;
import com.personalfinance.api.user.service.UserService;
import com.personalfinance.common.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        UserProfileResponse response = userService.getMyProfile();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/change-username")
    public ResponseEntity<MessageResponse> changeUsername(@Valid @RequestBody ChangeUsernameRequest request) {
        userService.changeUsername(request);
        MessageResponse response = MessageResponse.builder()
                .message("Your username has been changed successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<MessageResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        MessageResponse response = MessageResponse.builder()
                .message("Your password has been changed successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
