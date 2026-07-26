package com.personalfinance.api.wallet.repository;

import com.personalfinance.api.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

}
