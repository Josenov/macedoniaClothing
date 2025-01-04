package com.macedonia.macedonia.persistence.impl;

import com.macedonia.macedonia.entities.Clothing;
import com.macedonia.macedonia.persistence.ClothingDAO;
import com.macedonia.macedonia.repository.ClothingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClothingDAOImpl extends GenericDAOImpl<Clothing,Long, ClothingRepository> implements ClothingDAO {
    public ClothingDAOImpl(ClothingRepository clothingRepository){
        super (clothingRepository);
    }

    @Override
    protected Class<Clothing> getEntityClass() {
        return Clothing.class; // Devuelve la clase de la entidad
    }

    @Override
    protected String getEntityName() {
        return "Clothing"; // Devuelve el nombre de la entidad
    }

    @Override
    public Clothing findByNameAndInventoryId(String name, Long inventoryId) {
        // Delegar al repositorio para buscar la prenda
        return repository.findByNameAndInventoryId(name, inventoryId);
    }
}
