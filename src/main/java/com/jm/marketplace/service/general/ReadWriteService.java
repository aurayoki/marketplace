package com.jm.marketplace.service.general;

import java.util.List;

public interface ReadWriteService<T> {

    List<T> findAll();
    void saveOrUpdate(T t);
    void deleteById(Long id);

    T findById(Long id);

    void saveUser (T t);
}