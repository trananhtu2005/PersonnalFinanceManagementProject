package com.personalfinance.api.user.repository;

import com.personalfinance.api.user.entity.Username;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsernameRepository extends JpaRepository<Username, Integer> {

    Optional<Username> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Username> findByUserId(Integer userId);
}
