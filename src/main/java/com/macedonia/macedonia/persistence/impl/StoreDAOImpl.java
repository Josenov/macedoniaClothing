package com.macedonia.macedonia.persistence.impl;

import com.macedonia.macedonia.entities.Store;
import com.macedonia.macedonia.persistence.StoreDAO;
import com.macedonia.macedonia.repository.StoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDAOImpl extends GenericDAOImpl<Store, Long, StoreRepository> implements StoreDAO {

    public StoreDAOImpl(StoreRepository storeRepository){
        super(storeRepository);
    }

    @Override
    protected Class<Store> getEntityClass() {
        return null;
    }

    @Override
    protected String getEntityName() {
        return null;
    }
}
