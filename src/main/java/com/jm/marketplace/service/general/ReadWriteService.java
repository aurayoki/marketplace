package com.jm.marketplace.service.general;

import java.util.List;

public interface ReadWriteService<T, K> {

    List<T> findAll();

    void saveOrUpdate(T t);

    void deleteById(T t);

    void saveUser (T t);
}