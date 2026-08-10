package com.personalfinance.api.saving_goal.controller;

import com.personalfinance.api.saving_goal.dto.request.CreateSavingGoalRequest;
import com.personalfinance.api.saving_goal.dto.request.DepositRequest;
import com.personalfinance.api.saving_goal.dto.request.UpdateSavingGoalRequest;
import com.personalfinance.api.saving_goal.dto.response.SavingGoalResponse;
import com.personalfinance.api.saving_goal.service.SavingGoalService;
import com.personalfinance.common.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saving-goals")
@RequiredArgsConstructor
public class SavingGoalController {

    private final SavingGoalService savingGoalService;

    @GetMapping
    public ResponseEntity<Page<SavingGoalResponse>> getAllSavingGoals(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(savingGoalService.getAllSavingGoals(pageable));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createSavingGoal(@Valid @RequestBody CreateSavingGoalRequest request) {
        savingGoalService.createSavingGoal(request);
        MessageResponse response = MessageResponse.builder()
                .message("Saving goal has been created successfully!")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateSavingGoal(@PathVariable("id") Integer id, @Valid @RequestBody UpdateSavingGoalRequest request) {
        savingGoalService.updateSavingGoal(id, request);
        MessageResponse response = MessageResponse.builder()
                .message("Saving goal has been updated successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteSavingGoal(@PathVariable("id") Integer id) {
        savingGoalService.deleteSavingGoal(id);
        MessageResponse response = MessageResponse.builder()
                .message("Saving goal has been deleted successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<MessageResponse> deposit(@PathVariable("id") Integer id, @Valid @RequestBody DepositRequest request) {
        savingGoalService.deposit(id, request);
        MessageResponse response = MessageResponse.builder()
                .message("Deposit successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
