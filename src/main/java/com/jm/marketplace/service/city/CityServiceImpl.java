package com.jm.marketplace.service.city;

import com.jm.marketplace.dao.CityDao;
import com.jm.marketplace.model.City;
import com.jm.marketplace.service.general.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ReadWriteServiceImpl<City, Long> implements CityService {

    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao) {
        super(cityDao);
        this.cityDao = cityDao;
    }
}
