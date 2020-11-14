package com.babaev.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CrudDao<T, ID> {
    Optional<T> findById(ID id);
    void save(T model);
    void update (T model);
    void deleteById (ID id);
    void deleteAllByIds(Set<ID> ids);
    List<T> findAll();
}
