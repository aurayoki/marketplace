package com.jm.marketplace.service.general;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public abstract class ReadWriteServiceImpl<T, K> implements ReadWriteService<T, K> {
    protected JpaRepository<T, K> jpaRepository;

    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void saveOrUpdate(T t) {
        jpaRepository.save(t);
    }

    @Override
    public void deleteById(T t) {
        jpaRepository.delete(t);
    }

    @Override
    public void saveUser(T t) {

    }
}
