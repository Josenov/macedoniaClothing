package com.macedonia.macedonia.services;

import com.macedonia.macedonia.dto.ClothingDTO;
import com.macedonia.macedonia.entities.Clothing;

import java.util.List;

public interface ClothingService {
    ClothingDTO createClothing(ClothingDTO clothingDTO);
    Clothing updateClothing(Long id, Clothing clothing);

    void deleteClothing(Long id);
    Clothing getClothingById(Long id);

    List<Clothing> getAllClothing();

}
