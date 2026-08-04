package com.personalfinance.api.auth.service;

import com.personalfinance.api.auth.entity.RefreshToken;
import com.personalfinance.api.user.entity.User;

public interface RefreshTokenService {

    RefreshToken create(User user);

    RefreshToken getValidToken(String refreshToken);

    void revoke(RefreshToken refreshToken);

    void revokeAll(User user);
}
