package com.jm.marketplace.util.converter;

import com.jm.marketplace.dto.CityDto;
import com.jm.marketplace.model.City;
import com.jm.marketplace.service.city.CityService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * Конвертер для City
 * Для поля регистрации пользователя
 * Конвертирует город из принимаемой строки с frontend и приводит его к CityDto для сохранения в БД.
 */

@Component
public class CityDtoConverter implements ConditionalGenericConverter {

    private final CityService<City,Long> cityService;

    public CityDtoConverter(CityService<City,Long> cityService) {
        this.cityService = cityService;
    }

    @Override
    public boolean matches(TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor1) {
        return typeDescriptor.getType().equals(String.class) && (typeDescriptor1.getType().equals(CityDto.class));
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, CityDto.class));
    }

    @Override
    public Object convert(Object o, TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor1) {
        if (o != null && o.getClass() == String.class) {
            String id = (String) o;
            return cityService.findById(Long.parseLong(id));
        }
        return null;
    }
}
