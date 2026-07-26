package com.personalfinance.api.auth.repository;

import com.personalfinance.api.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

}
