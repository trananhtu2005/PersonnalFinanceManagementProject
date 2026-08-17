package com.personalfinance.api.saving_goal.service;

import com.personalfinance.api.saving_goal.dto.request.CreateSavingGoalRequest;
import com.personalfinance.api.saving_goal.dto.request.DepositRequest;
import com.personalfinance.api.saving_goal.dto.request.UpdateSavingGoalRequest;
import com.personalfinance.api.saving_goal.dto.response.SavingGoalResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SavingGoalService {

    Page<SavingGoalResponse> getAllSavingGoals(Pageable pageable);

    void createSavingGoal(CreateSavingGoalRequest request);

    void updateSavingGoal(Integer id, UpdateSavingGoalRequest request);

    void deleteSavingGoal(Integer id);

    void deposit(Integer id, DepositRequest request);

    List<SavingGoalResponse> getInProgressSavingGoals();
}
