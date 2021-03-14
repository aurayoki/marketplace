package com.jm.marketplace.util.geo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeoServiceTest {

    String f = "Санкт-Петербург, Маршала казакова 55";

    @Autowired
    GeoCoderService geoCoderService;



    @Test
    void getCoordinatesByCity() {
       /*
       Написать тесты
        */
        System.out.println(geoCoderService.getCoordinatesByCity(f).getLongitude());
        System.out.println(geoCoderService.getCoordinatesByCity(f).getLatitude());
    }
}