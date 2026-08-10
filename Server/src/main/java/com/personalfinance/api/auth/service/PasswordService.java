package com.personalfinance.api.auth.service;

import com.personalfinance.api.user.entity.User;

public interface PasswordService {

    void validatePassword(String password, String confirmPassword);

    String encode(String password);

    void checkPassword(String password, User user);
}
