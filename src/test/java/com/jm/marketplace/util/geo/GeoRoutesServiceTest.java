package com.jm.marketplace.util.geo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class GeoRoutesServiceTest {


    String adr1 = "Санкт-Петербург, Маршала казакова 55";
    String adr2 = "Санкт-Петербург, Ленинский-проспект 81";

    @Autowired
    GeoRoutesService geoRoutesService;

    @Test
    void getDistanceByCity() {
         /*
       Написать тесты
        */
        System.out.println(geoRoutesService.getDistanceByCity(adr1, adr2));
    }
}