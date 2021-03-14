package com.jm.marketplace.util.geo;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.exception.UserPhoneExistsException;
import com.jm.marketplace.model.geoStructure.Point;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.method.P;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class GeoCoderServiceTest {

    Point point = new Point(65.5555, 23.4242);

    @Mock
    GeoCoderService geoCoderService;



    @Test
    void getCoordinatesByCity() {
        given(geoCoderService.getCoordinatesByCity(anyString())).willReturn(point);

        assertThat(geoCoderService.getCoordinatesByCity(anyString())).isInstanceOf(Point.class);
    }

    @Test
    void getCoordinatesByCityNullPointerException() {
        given(geoCoderService.getCoordinatesByCity(anyString())).willThrow(NullPointerException.class);

        Throwable throwable = catchThrowable(() -> geoCoderService.getCoordinatesByCity(anyString()));

        assertThat(throwable).isInstanceOf(NullPointerException.class);

    }
}