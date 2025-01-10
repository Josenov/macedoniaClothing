package com.macedonia.macedonia.persistence;

import com.macedonia.macedonia.entities.Clothing;
import com.macedonia.macedonia.entities.Inventory;


public interface ClothingDAO extends GenericDAO<Clothing, Long>{

    Clothing findByNameAndInventory(String name, Inventory inventory);
    Clothing save(Clothing clothing);

}
