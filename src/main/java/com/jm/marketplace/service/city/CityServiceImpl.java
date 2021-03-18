package com.jm.marketplace.service.city;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dao.CityDao;
import com.jm.marketplace.dto.CityDto;
import com.jm.marketplace.exception.CityNotFoundException;
import com.jm.marketplace.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<City> getAllCity() {
       return cityDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public City findById(Long id) {
      return  cityDao.findById(id).orElseThrow(()->
                new CityNotFoundException(String.format("City not found by id: %s", id)));
    }
}
