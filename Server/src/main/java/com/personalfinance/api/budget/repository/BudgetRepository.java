package com.personalfinance.api.budget.repository;

import com.personalfinance.api.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

}
