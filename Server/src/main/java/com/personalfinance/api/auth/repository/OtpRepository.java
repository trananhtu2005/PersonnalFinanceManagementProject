package com.personalfinance.api.auth.repository;

import com.personalfinance.api.auth.entity.Otp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Integer> {

    Optional<Otp> findFirstByEmailOrderByCreatedAtDesc(String email);

    Optional<Otp> findByEmailAndCode(String email, String code);
}
