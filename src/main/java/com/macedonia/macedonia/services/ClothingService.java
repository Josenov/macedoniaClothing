package com.macedonia.macedonia.services;

import com.macedonia.macedonia.entities.Clothing;

import java.util.List;

public interface ClothingService {
    Clothing createClothing(Clothing clothing);
    Clothing updateClothing(Long id, Clothing clothing);

    void deleteClothing(Long id);
    Clothing getClothingById(Long id);

    List<Clothing> getAllClothing();

}
