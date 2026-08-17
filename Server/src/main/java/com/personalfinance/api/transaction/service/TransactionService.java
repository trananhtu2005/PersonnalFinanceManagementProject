package com.personalfinance.api.transaction.service;

import com.personalfinance.api.transaction.dto.request.CreateTransactionRequest;
import com.personalfinance.api.transaction.dto.request.UpdateTransactionRequest;
import com.personalfinance.api.transaction.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Page<TransactionResponse> getAllTransactions(Integer month, Integer year, Integer categoryId, Pageable pageable);

    void createTransaction(CreateTransactionRequest request);

    void updateTransaction(Integer id, UpdateTransactionRequest request);

    void deleteTransaction(Integer id);
}
