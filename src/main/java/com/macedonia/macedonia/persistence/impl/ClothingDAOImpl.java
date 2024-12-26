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
        return null;
    }

    @Override
    protected String getEntityName() {
        return null;
    }
}
