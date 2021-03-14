package com.jm.marketplace.util.geo;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 *
 * Сервис позволяет получить дистанцию в МЕТРАХ между несколькими адресами.
 * http://api.sputnik.ru/maps/routes - Сервис маршрутов.
 */
@Component
public class GeoRoutesService {

    private static final String BASE_URL = "http://routes.maps.sputnik.ru/osrm/router/viaroute?";

    private static final WebClient WEB_CLIENT = WebClient.create();

    private final GeoCoderService geoCoderService;

    @Autowired
    public GeoRoutesService(GeoCoderService geoCoderService) {
        this.geoCoderService = geoCoderService;
    }

    public int getDistanceByCity(String start_city, String end_city) {

        String start_point = "loc=" + geoCoderService.getCoordinatesByCity(start_city).getLatitude()
                + "," + geoCoderService.getCoordinatesByCity(start_city).getLongitude();

        String end_point = "loc=" + geoCoderService.getCoordinatesByCity(end_city).getLatitude()
                + "," + geoCoderService.getCoordinatesByCity(end_city).getLongitude();

        return  Objects.requireNonNull(WEB_CLIENT.get()
                .uri(BASE_URL + start_point + "&" + end_point)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block())
                .at("/route_summary/total_distance")
                .asInt();
    }
}
