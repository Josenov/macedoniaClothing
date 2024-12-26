package com.macedonia.macedonia.persistence;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    T save(T entity);
    boolean deleteById(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();

    List<T> findByField(String fieldName, Object value);
}
