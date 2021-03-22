package com.jm.marketplace.service.general;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public abstract class ReadWriteServiceImpl<T, ID> implements ReadWriteService<T, ID> {
    protected JpaRepository<T, ID> jpaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }
    @Transactional
    @Override
    public void saveOrUpdate(T t) {
        jpaRepository.save(t);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<T> findById(ID id) {
        return jpaRepository.findById(id);
    }
    @Transactional
    @Override
    public void deleteById(ID id) {
        jpaRepository.deleteById(id);
    }
}
