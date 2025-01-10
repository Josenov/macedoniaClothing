package com.macedonia.macedonia.services.impl;

import com.macedonia.macedonia.entities.Inventory;
import com.macedonia.macedonia.entities.Store;
import com.macedonia.macedonia.persistence.InventoryDAO;
import com.macedonia.macedonia.persistence.StoreDAO;
import com.macedonia.macedonia.services.StoreService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreDAO storeDAO;
    private final InventoryDAO inventoryDAO;

    public StoreServiceImpl(StoreDAO storeDAO, InventoryDAO inventoryDAO){

        this.storeDAO = storeDAO;
        this.inventoryDAO = inventoryDAO;
    }
    /*@Override
    public Store createStore(Store store) {
        return storeDAO.save(store);
    }*/

    @Override
    public Store createStore(Store store) {
        if (store.getInventory() == null) {
            Inventory inventory = new Inventory();
            inventory.setName("Inventario de " + store.getName());
            inventory.setLocation(store.getLocation());
            inventory.setTotalItems(0);
            inventory.setLastUpdated(LocalDateTime.now());
            store.setInventory(inventory);
        } else {
            Inventory existingInventory = inventoryDAO.findById(store.getInventory().getId())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrada con ID: " + store.getInventory().getId()));
            store.setInventory(existingInventory);
        }
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
            throw new RuntimeException("Tienda no encontrada con ID: " + id);
        }
    }

    @Override
    public void deleteStore(Long id) {
        Optional<Store> store = storeDAO.findById(id); // Busca el Store por ID
        if (store.isEmpty()) { // Verifica si el Store no existe
            throw new RuntimeException("Tienda no encontrada con ID: " + id);
        }
        storeDAO.deleteById(id); // Si existe, procede a eliminar
    }

    @Override
    public Store getStoreById(Long id) {
        return storeDAO.findById(id).orElseThrow(()->
                new RuntimeException(("Tienda no encontrada con ID: " + id)));
    }

    @Override
    public List<Store> getAllStores() {
        return storeDAO.findAll();
    }
}
