package com.jm.marketplace.util.geo;

import com.jm.marketplace.model.geoStructure.GeoStructure;
import com.jm.marketplace.model.geoStructure.Point;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 *
 * Сервис геокодирования позволяет получить географические координаты по его адресу.
 * http://api.sputnik.ru/maps/geocoder - Сервис геокодирования.
 */
@Component
public class GeoCoderService {

    private static final String BASE_URL = "http://search.maps.sputnik.ru/search/addr?q=";

    private static final WebClient WEB_CLIENT = WebClient.create();

    public Point getCoordinatesByCity(String city) {
        GeoStructure.Coordinate coordinate = Objects.requireNonNull(WEB_CLIENT.get()
                .uri(BASE_URL + city)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GeoStructure.class).block())
                .getResult()
                .getAddress()
                .stream()
                .map(x -> x.getFeatures()
                        .get(0)
                        .getGeometry()
                        .getGeometries()
                        .get(0))
                .findFirst()
                .orElse(null);

        return Point.builder()
                .longitude(Objects.requireNonNull(coordinate).getCoordinates().get(0))
                .latitude(Objects.requireNonNull(coordinate).getCoordinates().get(1))
                .build();
    }
}
