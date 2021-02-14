package com.jm.marketplace.config.mapper;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

@Component
public class MapperFacadeImpl implements MapperFacade {

    private final MapperFactory mapperFactory;

    @Autowired
    public MapperFacadeImpl(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> sourceObject, Class<D> classDestination) {
        return mapperFactory.getMapperFacade().mapAsList(sourceObject, classDestination);
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> classDestination) {
        return mapperFactory.getMapperFacade().map(sourceObject, classDestination);
    }

    @Override
    public <S, D> void map(S sourceObject, D destinationObject) {
        mapperFactory.getMapperFacade().map(sourceObject, destinationObject);
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> classDestination, String... excludeFields) {
        D map = mapperFactory.getMapperFacade().map(sourceObject, classDestination);
        Class<?> aClass = map.getClass();
        for (String excludeField : excludeFields) {
            Field field = ReflectionUtils.findField(aClass, excludeField);
            if (field != null) {
                field.setAccessible(true);
                Class<? extends Field> fieldClass = field.getClass();
                if (!fieldClass.isPrimitive()) {
                    ReflectionUtils.setField(field, map, null);
                }
            }
        }
        return map;
    }
}
