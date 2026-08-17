package com.personalfinance.api.budget.controller;

import com.personalfinance.api.budget.dto.request.CreateBudgetRequest;
import com.personalfinance.api.budget.dto.request.UpdateBudgetRequest;
import com.personalfinance.api.budget.dto.response.BudgetResponse;
import com.personalfinance.api.budget.service.BudgetService;
import com.personalfinance.api.category.dto.response.CategoryResponse;
import com.personalfinance.common.MessageResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping("/current-month")
    public ResponseEntity<List<BudgetResponse>> getCurrentMonthBudgets() {
        List<BudgetResponse> response = budgetService.getCurrentMonthBudgets();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<BudgetResponse>> getBudgetsByCategoryInYear(
            @RequestParam Integer categoryId,
            @RequestParam Integer year
    ) {
        List<BudgetResponse> response = budgetService.getBudgetsByCategoryInYear(categoryId, year);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<BudgetResponse>> getBudgetsByMonthAndYear(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {
        List<BudgetResponse> response = budgetService.getBudgetsByMonthAndYear(month, year);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/suggestion")
    public ResponseEntity<List<CategoryResponse>> getCategoriesForSuggestion() {
        List<CategoryResponse> response = budgetService.getCategoriesForSuggestion();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createBudget(@Valid @RequestBody CreateBudgetRequest request) {
        budgetService.createBudget(request);
        MessageResponse response = MessageResponse.builder()
                .message("Budget has been created successfully!")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateBudget(@PathVariable("id") Integer id, @Valid @RequestBody UpdateBudgetRequest request) {
        budgetService.updateBudget(id, request);
        MessageResponse response = MessageResponse.builder()
                .message("Budget has been updated successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteBudget(@PathVariable("id") Integer id) {
        budgetService.deleteBudget(id);
        MessageResponse response = MessageResponse.builder()
                .message("Budget has been deleted successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
