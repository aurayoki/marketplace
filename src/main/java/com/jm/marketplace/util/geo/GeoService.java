package com.jm.marketplace.util.geo;

import com.jm.marketplace.dto.PointDto;
import com.jm.marketplace.model.geoStructure.GeoStructure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
public class GeoService {

    @Value(value = "http://search.maps.sputnik.ru/search/addr?q=")
    private String baseURL;

    private static final WebClient WEB_CLIENT = WebClient.create();

    public PointDto getCoordinatesByCity(String city) {
        GeoStructure.Coordinate coordinate = Objects.requireNonNull(WEB_CLIENT.get()
                .uri(baseURL + city)
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

        return PointDto.builder()
                .longitude(Objects.requireNonNull(coordinate).getCoordinates().get(0))
                .latitude(Objects.requireNonNull(coordinate).getCoordinates().get(1))
                .build();
    }
}
