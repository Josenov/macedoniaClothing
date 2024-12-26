package com.macedonia.macedonia.controllers;

import com.macedonia.macedonia.entities.Clothing;
import com.macedonia.macedonia.services.ClothingService;
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
    public ResponseEntity<Clothing> createClothing(@RequestBody Clothing clothing){
        Clothing createdClothing = clothingService.createClothing(clothing);
        return new ResponseEntity<>(createdClothing, HttpStatus.CREATED);


    }

    //Get all Items
    @GetMapping
    public ResponseEntity<List<Clothing>> getAllClothing(){
        List<Clothing> clothingList = clothingService.getAllClothing();
        return new ResponseEntity<>(clothingList, HttpStatus.OK);
    }

    // Update Clothing
    @PutMapping("/{id}")
    public ResponseEntity<Clothing> updateClothing(@PathVariable Long id, @RequestBody Clothing clothing) {
        Clothing updatedClothing = clothingService.updateClothing(id, clothing);
        return ResponseEntity.ok(updatedClothing);
    }

    // Delete Clothing
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteClothing(@PathVariable Long id) {
        clothingService.deleteClothing(id);
        // Crear un mensaje de éxito
        Map<String, String> response = new HashMap<>();
        response.put("message", "Prenda eliminada con éxito");

        return ResponseEntity.ok(response); // Retorna el mensaje con el código 200 OK
    }

    //Get Clothing by ID
    @GetMapping("/{id}")
    public ResponseEntity<Clothing> getClothingById(@PathVariable Long id) {
        Clothing clothing = clothingService.getClothingById(id);
        return ResponseEntity.ok(clothing);
    }
}
