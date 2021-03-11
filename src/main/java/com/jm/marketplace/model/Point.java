package com.jm.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Класс точки, хранит значения в градусах
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    private String type;

    private List<Double> coordinates;

}

