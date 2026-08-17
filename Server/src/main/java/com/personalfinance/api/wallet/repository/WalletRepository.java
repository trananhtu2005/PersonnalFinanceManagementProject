package com.personalfinance.api.wallet.repository;

import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.wallet.entity.Wallet;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    List<Wallet> findByUserAndDeletedFalse(User user);

    Optional<Wallet> findByIdAndUserAndDeletedFalse(Integer id, User user);

    boolean existsByUserAndNameAndDeletedFalse(User user, String name);

    @Query("""
           SELECT COALESCE(SUM(w.balance), 0)
           FROM Wallet w
           WHERE w.user = :user
           AND w.deleted = false
           """)
    BigDecimal sumBalanceByUserAndDeletedFalse(@Param("user") User user);
}
