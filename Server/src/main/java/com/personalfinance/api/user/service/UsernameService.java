package com.personalfinance.api.user.service;

import com.personalfinance.api.user.entity.Username;

public interface UsernameService {

    void checkDuplicateUsername(String username);

    void checkUsernameChangeTime(Username username);
}
