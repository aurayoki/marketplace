package com.jm.marketplace.util.geo;

import com.jm.marketplace.config.WebClientConfig;
import com.jm.marketplace.model.geoStructure.GeoStructure;
import com.jm.marketplace.model.geoStructure.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Сервис геокодирования позволяет получить географические координаты по его адресу.
 * http://api.sputnik.ru/maps/geocoder - Сервис геокодирования.
 */
@Component
public class GeoCoderService {

    private static final String BASE_URL = "http://search.maps.sputnik.ru/search/addr?q=";

    private final WebClientConfig webClientConfig;

    @Autowired
    public GeoCoderService(WebClientConfig webClientConfig) {
        this.webClientConfig = webClientConfig;
    }

    public Point getCoordinatesByAddress(String address) {
        GeoStructure.Coordinate coordinate = Objects.requireNonNull(webClientConfig
                .requestHeadersUriSpec()
                .uri(BASE_URL + address)
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
