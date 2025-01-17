package com.macedonia.macedonia.services;

import com.macedonia.macedonia.dto.TransactionDTO;
import com.macedonia.macedonia.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO updatedTransaction (Long id, TransactionDTO transactionDTO);


    void deleteTransaction(Long id);
    Optional<Transaction> getTransactionById(Long id);

    List<Transaction> getAllTransactions();

    double getTotalInvestmentByMonth(int year, int month);
    double getTotalSalesByMonth(int year, int month);
}
