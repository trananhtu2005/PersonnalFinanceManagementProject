package com.personalfinance.api.transaction.controller;

import com.personalfinance.api.transaction.dto.request.CreateTransactionRequest;
import com.personalfinance.api.transaction.dto.request.UpdateTransactionRequest;
import com.personalfinance.api.transaction.dto.response.TransactionResponse;
import com.personalfinance.api.transaction.service.TransactionService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getAllTransactions(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer categoryId,
            @PageableDefault(
                    sort = "date",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return ResponseEntity.ok(transactionService
                .getAllTransactions(month, year, categoryId, pageable));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        transactionService.createTransaction(request);
        MessageResponse response = MessageResponse.builder()
                .message("Transaction has been created successfully!")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateTransaction(@PathVariable("id") Integer id, @Valid @RequestBody UpdateTransactionRequest request) {
        transactionService.updateTransaction(id, request);
        MessageResponse response = MessageResponse.builder()
                .message("Transaction has been updated successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteTransaction(@PathVariable("id") Integer id) {
        transactionService.deleteTransaction(id);
        MessageResponse response = MessageResponse.builder()
                .message("Transaction has been deleted successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
