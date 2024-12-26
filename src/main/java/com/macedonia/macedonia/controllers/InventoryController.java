package com.macedonia.macedonia.controllers;

import com.macedonia.macedonia.entities.Inventory;
import com.macedonia.macedonia.services.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;


    }

    // Create a new inventory
    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory createdInventory = inventoryService.createInventory(inventory);
        return ResponseEntity.ok(createdInventory);
    }

    // Get inventory by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/{inventoryId}/add-clothing/{clothingId}")
    public ResponseEntity<Inventory> addClothingToInventory(
            @PathVariable Long inventoryId, @PathVariable Long clothingId){
        Inventory updatedInventory = inventoryService.addClothingToInventory(inventoryId,clothingId);
        return ResponseEntity.ok(updatedInventory);
    }



}