package com.macedonia.macedonia.services;

import com.macedonia.macedonia.entities.Inventory;

import java.util.List;

public interface InventoryService {
    Inventory createInventory (Inventory inventory);

    Inventory updateInventory (Long id, Inventory inventory);

    Inventory addClothingToInventory(Long inventoryId, Long clothingId);

    void deleteInventory (Long id);

    Inventory getInventoryById(Long id);

    List<Inventory> getAllInventories();
}
