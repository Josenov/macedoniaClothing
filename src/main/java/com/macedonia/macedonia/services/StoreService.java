package com.macedonia.macedonia.services;

import com.macedonia.macedonia.entities.Store;

import java.util.List;

public interface StoreService {

    Store createStore(Store store);

    Store updatedStore(Long id, Store store);

    void deleteStore(Long id);

    Store getStoreById(Long id);

    List<Store> getAllStores();
}
