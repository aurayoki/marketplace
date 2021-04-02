package com.jm.marketplace.util.geo;

import com.fasterxml.jackson.databind.JsonNode;
import com.jm.marketplace.config.WebClientConfig;
import com.jm.marketplace.model.geoStructure.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Сервис позволяет получить дистанцию в МЕТРАХ между несколькими адресами.
 * http://api.sputnik.ru/maps/routes - Сервис маршрутов.
 */
@Component
public class GeoRoutesService {

    private static final String BASE_URL = "http://routes.maps.sputnik.ru/osrm/router/viaroute?";

    private final WebClientConfig webClientConfig;

    @Autowired
    public GeoRoutesService(WebClientConfig webClientConfig) {
        this.webClientConfig = webClientConfig;
    }

    public int getDistanceByAddress(Point start_point, Point end_point) {

        String start = "loc=" + start_point.getLatitude()
                + "," + start_point.getLongitude();

        String end = "loc=" + end_point.getLatitude()
                + "," + end_point.getLongitude();

        return Objects.requireNonNull(webClientConfig
                .requestHeadersUriSpec()
                .uri(BASE_URL + start + "&" + end)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block())
                .at("/route_summary/total_distance")
                .asInt();
    }
}
