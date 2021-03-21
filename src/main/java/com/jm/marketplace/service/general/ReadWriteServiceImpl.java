package com.jm.marketplace.service.general;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public abstract class ReadWriteServiceImpl<T, ID> implements ReadWriteService<T, ID> {
    protected JpaRepository<T, ID> jpaRepository;

    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void saveOrUpdate(T t) {
        jpaRepository.save(t);
    }

    @Override
    public Optional<T> findById(ID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void deleteById(ID id) {
        jpaRepository.deleteById(id);
    }
}
