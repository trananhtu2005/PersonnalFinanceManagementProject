package com.personalfinance.api.saving_goal.repository;

import com.personalfinance.api.saving_goal.entity.SavingGoal;
import com.personalfinance.api.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingGoalRepository extends JpaRepository<SavingGoal, Integer> {

    Page<SavingGoal> findByUser(User user, Pageable pageable);

    Optional<SavingGoal> findByIdAndUser(Integer id, User user);
}
