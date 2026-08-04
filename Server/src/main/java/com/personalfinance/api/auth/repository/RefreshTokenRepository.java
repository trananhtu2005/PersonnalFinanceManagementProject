package com.personalfinance.api.auth.repository;

import com.personalfinance.api.auth.entity.RefreshToken;
import com.personalfinance.api.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    List<RefreshToken> findByUserAndRevokedFalse(User user);
}
