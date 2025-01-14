package com.macedonia.macedonia.controllers;

import com.macedonia.macedonia.dto.TransactionDTO;
import com.macedonia.macedonia.entities.Transaction;
import com.macedonia.macedonia.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            // Convertir DTO a entidad y crear la transacción
            TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);

            return ResponseEntity.status(201).body(createdTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error al crear transacción: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok) // Si existe, devuelve 200 con el objeto
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no, devuelve 404
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) String type) {
        List<Transaction> transactions = transactionService.getAllTransactions();

        // Filtros opcionales
        if (storeId != null) {
            transactions = transactions.stream()
                    .filter(t -> t.getStore().getId().equals(storeId))
                    .toList();
        }
        if (type != null) {
            transactions = transactions.stream()
                    .filter(t -> t.getType().equalsIgnoreCase(type))
                    .toList();
        }

        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionDetails) {
        Transaction updatedTransaction = transactionService.updatedTransaction(id, transactionDetails);
        return ResponseEntity.ok(updatedTransaction);
    }
}
