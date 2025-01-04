package com.macedonia.macedonia.controllers;

import com.macedonia.macedonia.entities.Inventory;
import com.macedonia.macedonia.services.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;


    }

    // Crear un nuevo inventario
    @PostMapping
    public ResponseEntity<Map<String, Object>> createInventory(@RequestBody Inventory inventory) {
        Inventory createdInventory = inventoryService.createInventory(inventory);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Inventario creado con éxito");
        response.put("inventory", createdInventory);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Obtener inventario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("inventory", inventory);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Obtener todos los inventarios
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllInventories() {
        List<Inventory> inventories = inventoryService.getAllInventories();

        List<Map<String, Object>> response = inventories.stream().map(inventory -> {
            Map<String, Object> inventoryData = new HashMap<>();
            inventoryData.put("id", inventory.getId());
            inventoryData.put("name", inventory.getName());
            inventoryData.put("location", inventory.getLocation());
            return inventoryData;
        }).toList();

        return ResponseEntity.ok(response);
    }

    // Agregar prenda a un inventario existente
    @PutMapping("/{inventoryId}/add-clothing/{clothingId}")
    public ResponseEntity<Map<String, Object>> addClothingToInventory(
            @PathVariable Long inventoryId, @PathVariable Long clothingId) {
        Inventory updatedInventory = inventoryService.addClothingToInventory(inventoryId, clothingId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Prenda añadida al inventario con éxito");
        response.put("updatedInventory", updatedInventory);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Eliminar Inventario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteInventory(@PathVariable Long id) {

        // Crear un mensaje de éxito
        try {
            inventoryService.deleteInventory(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Inventario eliminado con éxito");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El inventario con ID " + id + " no se encuentra en la base de datos o ya fue eliminado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }




}