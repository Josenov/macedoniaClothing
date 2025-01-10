package com.macedonia.macedonia.controllers;

import com.macedonia.macedonia.dto.ClothingDTO;
import com.macedonia.macedonia.entities.Clothing;
import com.macedonia.macedonia.services.ClothingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clothing")
public class ClothingController {

    private final ClothingService clothingService;

    public ClothingController(ClothingService clothingService){
        this.clothingService = clothingService;
    }

    //Create Clothing Item
    @PostMapping
    public ResponseEntity<Map<String, Object>> createClothing(@RequestBody ClothingDTO clothingDTO) {
        ClothingDTO createdClothing = clothingService.createClothing(clothingDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Prenda creada con éxito");
        response.put("data", createdClothing);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Get all Items
    @GetMapping
    public ResponseEntity<List<Clothing>> getAllClothing(){
        List<Clothing> clothingList = clothingService.getAllClothing();
        return new ResponseEntity<>(clothingList, HttpStatus.OK);
    }

    // Update Clothing

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateClothing(@PathVariable Long id, @RequestBody Clothing clothing) {
        Clothing updatedClothing = clothingService.updateClothing(id, clothing);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Prenda editada con éxito");
        response.put("data", updatedClothing);
        return ResponseEntity.ok(response);
    }


    // Delete Clothing
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteClothing(@PathVariable Long id) {

        // Crear un mensaje de éxito
        try {
            clothingService.deleteClothing(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Prenda eliminada con éxito");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "La prenda con ID " + id + " no se encuentra en la base de datos o ya fue eliminada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    //Get Clothing by ID
    @GetMapping("/{id}")
    public ResponseEntity<Clothing> getClothingById(@PathVariable Long id) {
        Clothing clothing = clothingService.getClothingById(id);
        return ResponseEntity.ok(clothing);
    }
}
