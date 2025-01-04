package com.macedonia.macedonia.persistence;

import com.macedonia.macedonia.entities.Clothing;


public interface ClothingDAO extends GenericDAO<Clothing, Long>{

    Clothing findByNameAndInventoryId(String name, Long inventoryId);
    Clothing save(Clothing clothing);

}
