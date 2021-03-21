package com.jm.marketplace.service.city;

import com.jm.marketplace.exception.CityNotFoundException;
import com.jm.marketplace.model.City;
import com.jm.marketplace.service.general.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService<City, Long> {

    private final ReadWriteService<City, Long> readWriteService;

    @Autowired
    public CityServiceImpl(@Lazy ReadWriteService<City, Long> readWriteService) {
        this.readWriteService = readWriteService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<City> findAll () {
        return readWriteService.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<City> findById(Long id) {
        return Optional.ofNullable(readWriteService.findById(id).orElseThrow(() ->
                new CityNotFoundException(String.format("City not found by id: %s", id))));
    }

    @Override
    public void saveOrUpdate(City city) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
