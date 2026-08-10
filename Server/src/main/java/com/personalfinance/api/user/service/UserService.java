package com.personalfinance.api.user.service;

import com.personalfinance.api.user.dto.request.ChangePasswordRequest;
import com.personalfinance.api.user.dto.request.ChangeUsernameRequest;
import com.personalfinance.api.user.dto.response.UserProfileResponse;

public interface UserService {

    UserProfileResponse getMyProfile();

    void changeUsername(ChangeUsernameRequest request);

    void changePassword(ChangePasswordRequest request);
}
