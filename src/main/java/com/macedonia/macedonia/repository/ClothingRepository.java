package com.macedonia.macedonia.repository;

import com.macedonia.macedonia.entities.Clothing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {

    Clothing findByNameAndInventoryId(String name, Long inventoryId);


}
