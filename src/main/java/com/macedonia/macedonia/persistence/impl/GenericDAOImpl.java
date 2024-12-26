package com.macedonia.macedonia.persistence.impl;

import com.macedonia.macedonia.entities.Transaction;
import com.macedonia.macedonia.persistence.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class GenericDAOImpl <T, ID, R extends JpaRepository<T,ID>> implements GenericDAO <T,ID> {
    protected final R repository;

    @PersistenceContext
    private EntityManager entityManager;

    public GenericDAOImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public boolean deleteById(ID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id); // Eliminar el registro del repositorio.
            return true; // Retornar `true` si la eliminación fue exitosa.
        }
        return false; // Retornar `false` si no existe el ID.
    }

    @Override
    public Optional<T> findById(ID id){
        return repository.findById(id);
    }


    @Override
    public List<T> findAll(){
        return repository.findAll();
    }

    @Override
    public List<T> findByField(String fieldName, Object value) {
        String queryString = "SELECT t FROM " + getEntityName() + " t WHERE t." + fieldName + " = :value";
        TypedQuery<T> query = entityManager.createQuery(queryString, getEntityClass());
        query.setParameter("value", value);
        return query.getResultList();
    }

    // Métodos abstractos para obtener la entidad y el nombre de la tabla
    protected abstract Class<T> getEntityClass();
    protected abstract String getEntityName();


}
