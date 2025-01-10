package com.macedonia.macedonia.controllers;

import com.macedonia.macedonia.entities.Store;
import com.macedonia.macedonia.services.StoreService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    /*@PostMapping
    public ResponseEntity<Store> createStore(@RequestBody Store store){
        return ResponseEntity.ok(storeService.createStore(store));
    }*/

    /*@PostMapping
    public ResponseEntity<?> createStore(@RequestBody Store store) {
        try {
            Store createdStore = storeService.createStore(store);
            return ResponseEntity.status(201).body(createdStore);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating store: " + e.getMessage());
        }
    }*/

    @PostMapping
    public ResponseEntity<Map<String, Object>> createStore(@RequestBody Store store) {
        Map<String, Object> response = new HashMap<>();
        try {
            Store createdStore = storeService.createStore(store);

            // Mensaje informativo
            response.put("message", "Tienda creada con éxito. El inventario asociado fue generado automáticamente. Puedes actualizar los datos del inventario en el endpoint PUT /api/inventories/{id}.");
            response.put("data", createdStore);

            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("message", "Error al crear la tienda.");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store store) {
        return ResponseEntity.ok(storeService.updatedStore(id, store));
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id){
        storeService.deleteStore((id));
        return ResponseEntity.noContent().build();
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id); // Si lanza una excepción, el manejador global se encarga
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tienda eliminada con éxito");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }


}
