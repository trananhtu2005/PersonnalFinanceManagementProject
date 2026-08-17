package com.personalfinance.api.user.repository;

import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.repository.projection.MonthlyUserProjection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
        SELECT COUNT(u)
        FROM User u
        WHERE u.createdAt >= :startDate
        AND u.createdAt < :endDate
    """)
    long countUsersByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
        SELECT
        MONTH(u.createdAt) AS month,
        COUNT(u) AS count
        FROM User u
        WHERE u.createdAt >= :startDate
        AND u.createdAt < :endDate
        GROUP BY MONTH(u.createdAt)
        ORDER BY MONTH(u.createdAt)
    """)
    List<MonthlyUserProjection> countUsersByMonth(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
