package com.jm.marketplace.model.geoStructure;

import lombok.*;

/**
 * Координаты
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    /**
     * Долгота
     */
    private Double longitude;

    /**
     * Широта
     */
    private Double latitude;
}

