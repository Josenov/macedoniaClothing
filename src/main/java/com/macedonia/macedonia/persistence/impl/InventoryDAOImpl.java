package com.macedonia.macedonia.persistence.impl;


import com.macedonia.macedonia.entities.Inventory;
import com.macedonia.macedonia.persistence.InventoryDAO;
import com.macedonia.macedonia.repository.InventoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryDAOImpl extends GenericDAOImpl<Inventory, Long, InventoryRepository> implements InventoryDAO {

    public InventoryDAOImpl(InventoryRepository inventoryRepository){
        super(inventoryRepository);
    }

    @Override
    protected Class<Inventory> getEntityClass() {

        return Inventory.class;
    };

    @Override
    protected String getEntityName() {
        return "Inventory";
    }
}
