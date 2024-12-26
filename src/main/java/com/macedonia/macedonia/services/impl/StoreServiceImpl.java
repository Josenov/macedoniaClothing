package com.macedonia.macedonia.services.impl;

import com.macedonia.macedonia.entities.Store;
import com.macedonia.macedonia.persistence.StoreDAO;
import com.macedonia.macedonia.services.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreDAO storeDAO;

    public StoreServiceImpl(StoreDAO storeDAO){
        this.storeDAO = storeDAO;
    }
    @Override
    public Store createStore(Store store) {
        return storeDAO.save(store);
    }

    @Override
    public Store updatedStore(Long id, Store store) {
        Optional<Store> existingStore = storeDAO.findById(id);
        if(existingStore.isPresent()){
            Store updatedStore = existingStore.get();
            updatedStore.setName(store.getName());
            updatedStore.setLocation(store.getLocation());
            return storeDAO.save(updatedStore);
        } else {
            throw new RuntimeException("Store not found with ID: " + id);
        }
    }

    @Override
    public void deleteStore(Long id) {
        storeDAO.deleteById(id);
    }

    @Override
    public Store getStoreById(Long id) {
        return storeDAO.findById(id).orElseThrow(()->
                new RuntimeException(("Store not found with ID: " + id)));
    }

    @Override
    public List<Store> getAllStores() {
        return storeDAO.findAll();
    }
}
