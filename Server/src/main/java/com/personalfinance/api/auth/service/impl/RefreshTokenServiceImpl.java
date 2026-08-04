package com.personalfinance.api.auth.service.impl;

import com.personalfinance.api.auth.entity.RefreshToken;
import com.personalfinance.api.auth.repository.RefreshTokenRepository;
import com.personalfinance.api.auth.service.RefreshTokenService;
import com.personalfinance.api.user.entity.User;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import com.personalfinance.security.jwt.JwtService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Override
    public RefreshToken create(User user) {
        String refreshToken = jwtService.generateRefreshToken(user);
        RefreshToken token = RefreshToken.builder()
                .refreshToken(refreshToken)
                .expireAt(jwtService.getRefreshExpiredAt())
                .revoked(false)
                .user(user)
                .build();

        return refreshTokenRepository.save(token);
    }

    @Override
    public RefreshToken getValidToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository
                .findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));

        if (token.isRevoked()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        if (jwtService.isTokenExpired(refreshToken)) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        return token;
    }

    @Override
    public void revoke(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void revokeAll(User user) {
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByUserAndRevokedFalse(user);

        for (RefreshToken refreshToken : refreshTokens) {
            refreshToken.setRevoked(true);
        }

        refreshTokenRepository.saveAll(refreshTokens);
    }
}
