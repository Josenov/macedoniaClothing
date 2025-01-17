package com.macedonia.macedonia.persistence;

import com.macedonia.macedonia.entities.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionDAO extends GenericDAO<Transaction, Long>{

    @Query("SELECT t FROM Transaction t WHERE t.type = :type AND YEAR(t.transactionDate) = :year AND MONTH(t.transactionDate) = :month")
    List<Transaction> findByTypeAndDate(@Param("type") String type, @Param("year") int year, @Param("month") int month);



}
