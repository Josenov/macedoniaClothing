package com.macedonia.macedonia.services.impl;

import com.macedonia.macedonia.entities.Clothing;
import com.macedonia.macedonia.entities.Store;
import com.macedonia.macedonia.entities.Transaction;
import com.macedonia.macedonia.persistence.ClothingDAO;
import com.macedonia.macedonia.persistence.StoreDAO;
import com.macedonia.macedonia.persistence.TransactionDAO;
import com.macedonia.macedonia.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO;
    private final ClothingDAO clothingDAO;
    private final StoreDAO storeDAO;

    public TransactionServiceImpl(TransactionDAO transactionDAO, ClothingDAO clothingDAO, StoreDAO storeDAO) {
        this.transactionDAO = transactionDAO;
        this.clothingDAO = clothingDAO;
        this.storeDAO = storeDAO;
    }
    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionDAO.save(transaction);
    }

    @Override
    public Transaction updatedTransaction(Long id, Transaction transactionDetails) {
        Transaction existingTransaction = transactionDAO.findById(id).orElseThrow(() ->
                new RuntimeException("Transaction not found with ID: " + id)
        );

        // Actualizar los campos de la transacción
        existingTransaction.setType(transactionDetails.getType());
        existingTransaction.setAmount(transactionDetails.getAmount());
        existingTransaction.setTransactionDate(transactionDetails.getTransactionDate());
        existingTransaction.setNotes(transactionDetails.getNotes());



        return transactionDAO.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionDAO.deleteById(id);
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionDAO.findById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll();
    }
}
