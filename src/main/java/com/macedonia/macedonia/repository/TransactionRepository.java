package com.macedonia.macedonia.repository;

import com.macedonia.macedonia.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
