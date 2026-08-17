package com.personalfinance.api.budget.service;

import com.personalfinance.api.budget.dto.request.CreateBudgetRequest;
import com.personalfinance.api.budget.dto.request.UpdateBudgetRequest;
import com.personalfinance.api.budget.dto.response.BudgetResponse;
import com.personalfinance.api.category.dto.response.CategoryResponse;
import com.personalfinance.api.category.entity.Category;
import com.personalfinance.api.user.entity.User;
import java.time.YearMonth;
import java.util.List;

public interface BudgetService {

    List<BudgetResponse> getCurrentMonthBudgets();

    List<BudgetResponse> getBudgetsByCategoryInYear(Integer categoryId, Integer year);

    List<BudgetResponse> getBudgetsByMonthAndYear(Integer month, Integer year);

    List<CategoryResponse> getCategoriesForSuggestion();

    void createBudget(CreateBudgetRequest request);

    void updateBudget(Integer id, UpdateBudgetRequest request);

    void deleteBudget(Integer id);

    void updateSpent(User user, Category category, YearMonth yearMonth);
}
