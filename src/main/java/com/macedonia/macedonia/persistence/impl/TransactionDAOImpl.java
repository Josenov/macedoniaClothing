package com.macedonia.macedonia.persistence.impl;

import com.macedonia.macedonia.entities.Transaction;
import com.macedonia.macedonia.persistence.TransactionDAO;
import com.macedonia.macedonia.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionDAOImpl extends GenericDAOImpl<Transaction, Long, TransactionRepository>  implements TransactionDAO {

    public TransactionDAOImpl(TransactionRepository transactionRepository){

        super(transactionRepository);
    }

    @Override
    protected Class<Transaction> getEntityClass() {
        return Transaction.class;
    }

    @Override
    protected String getEntityName() {
        return "Transaction";
    }


    @Override
    public List<Transaction> findByTypeAndDate(String type, int year, int month) {
        // Usar el repositorio para realizar una consulta personalizada
        return repository.findByTypeAndDate(type, year, month);
    }
}
