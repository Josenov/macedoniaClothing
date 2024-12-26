package com.macedonia.macedonia.persistence.impl;

import com.macedonia.macedonia.entities.Transaction;
import com.macedonia.macedonia.persistence.TransactionDAO;
import com.macedonia.macedonia.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

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


}
