package com.personalfinance.api.saving_goal.repository;

import com.personalfinance.api.saving_goal.entity.SavingGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingGoalRepository extends JpaRepository<SavingGoal, Integer> {

}
