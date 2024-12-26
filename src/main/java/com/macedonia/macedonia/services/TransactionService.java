package com.macedonia.macedonia.services;

import com.macedonia.macedonia.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Transaction updatedTransaction (Long id, Transaction transaction);
    void deleteTransaction(Long id);
    Optional<Transaction> getTransactionById(Long id);

    List<Transaction> getAllTransactions();
}
