package com.macedonia.macedonia.repository;

import com.macedonia.macedonia.entities.Clothing;

import com.macedonia.macedonia.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {

    Clothing findByNameAndInventory(String name, Inventory inventory);




}
