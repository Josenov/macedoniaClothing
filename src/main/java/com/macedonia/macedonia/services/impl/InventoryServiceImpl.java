package com.macedonia.macedonia.services.impl;

import com.macedonia.macedonia.entities.Clothing;
import com.macedonia.macedonia.entities.Inventory;
import com.macedonia.macedonia.persistence.ClothingDAO;
import com.macedonia.macedonia.persistence.InventoryDAO;
import com.macedonia.macedonia.services.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryDAO inventoryDAO;

    private final ClothingDAO clothingDAO;

    public InventoryServiceImpl(InventoryDAO inventoryDAO, ClothingDAO clothingDAO){
        this.inventoryDAO=inventoryDAO;
        this.clothingDAO=clothingDAO;

    }

    @Override
    public Inventory createInventory(Inventory inventory){
        return inventoryDAO.save(inventory);
    }

    @Override
    public Inventory updateInventory(Long id, Inventory updatedInventory){
        Optional<Inventory> existingInventoryOptional = inventoryDAO.findById(id);
        if (existingInventoryOptional.isPresent()) {
            Inventory existingInventory = existingInventoryOptional.get();

            existingInventory.setClothingItems(updatedInventory.getClothingItems());
            existingInventory.setTotalItems(updatedInventory.getClothingItems().size());
            existingInventory.updateLastUpdated();

            return inventoryDAO.save(existingInventory);
        } else {
            throw new RuntimeException("Inventory not found with ID: " + id);
        }
    }


    public void deleteInventory(Long id){
        inventoryDAO.deleteById(id);
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryDAO.findById(id).orElseThrow(() ->
                new RuntimeException("Inventory not found with ID: " + id)
        );
    }

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryDAO.findAll();
    }

    @Override
    public Inventory addClothingToInventory (Long inventoryId, Long clothingId){
        Inventory inventory = inventoryDAO.findById(inventoryId).orElseThrow(()-> new RuntimeException("Inventory not found with ID: " + inventoryId)
        );

        Clothing clothing = clothingDAO.findById(clothingId).orElseThrow(() ->
                new RuntimeException("Clothing not found with ID: " + clothingId)
        );

        // Vincular la prenda con el inventario
        clothing.setInventory(inventory);
        inventory.getClothingItems().add(clothing);

        // Guardar los cambios
        clothingDAO.save(clothing); // Actualiza la prenda
        return inventoryDAO.save(inventory); // Actualiza el inventario


    }

}
