package com.personalfinance.api.auth.repository;

import com.personalfinance.api.auth.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Integer> {

}
