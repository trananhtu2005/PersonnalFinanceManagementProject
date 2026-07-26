package com.personalfinance.api.transaction.repository;

import com.personalfinance.api.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
