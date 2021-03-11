package com.jm.marketplace.dto;

import lombok.*;

/**
 * ДТО координаты
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {

    /**
     * Долгота
     */
    private Double longitude;

    /**
     * Широта
     */
    private Double latitude;
}