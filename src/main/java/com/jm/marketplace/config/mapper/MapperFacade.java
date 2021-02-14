package com.jm.marketplace.config.mapper;

import java.util.List;

/**
 * Используется для преобразования объектов
 */
public interface MapperFacade {

    /**
     * Метод преобразовывает коллекцию типов к указанному типу
     *
     * @param sourceObject     коллекция иточников которые нужно привести к нужному типу
     * @param classDestination тип к которому нужно привести
     * @return List объектов нужного типа
     */
    <S, D> List<D> mapAsList(Iterable<S> sourceObject, Class<D> classDestination);

    /**
     * Метод преобразовывает один тип объекта к указанному типу
     *
     * @param sourceObject     объект который нужно привести к нужному типу
     * @param classDestination тип к которому нужно привести
     * @return Объект нужного типа
     */
    <S, D> D map(S sourceObject, Class<D> classDestination);

    /**
     * Метод копирует значения из одного объекта в другой
     *
     * @param sourceObject      объект источник данных
     * @param destinationObject объект приемник данных
     */
    <S, D> void map(S sourceObject, D destinationObject);

    /**
     * Метод преобразовывает один тип объекта к указанному типу
     *
     * @param sourceObject     объект который нужно привести к нужному типу
     * @param classDestination тип к которому нужно привести
     * @param excludeFields    имена полей которые нужно исключить из маппинга (значение null)
     * @return Объект нужного типа
     */
    <S, D> D map(S sourceObject, Class<D> classDestination, String... excludeFields);
}
