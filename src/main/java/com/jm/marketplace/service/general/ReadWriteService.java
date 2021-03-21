package com.jm.marketplace.service.general;

import java.util.List;
import java.util.Optional;

public interface ReadWriteService<T, ID> {

    List<T> findAll();

    void saveOrUpdate(T t);

    Optional<T> findById(ID id);

    void deleteById(ID id);
}