package com.personalfinance.api.wallet.repository;

import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.wallet.entity.Wallet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    List<Wallet> findByUserAndDeletedFalse(User user);

    Optional<Wallet> findByIdAndUserAndDeletedFalse(Integer id, User user);

    boolean existsByUserAndNameAndDeletedFalse(User user, String name);
}
