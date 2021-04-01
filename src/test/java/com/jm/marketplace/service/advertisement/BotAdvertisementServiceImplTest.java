package com.jm.marketplace.service.advertisement;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dao.AdvertisementDao;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.exception.AdvertisementNotFoundException;
import com.jm.marketplace.filter.AdvertisementFilter;
import com.jm.marketplace.model.Advertisement;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
class BotAdvertisementServiceImplTest {

    @Autowired
    private AdvertisementService advertisementService;

    @MockBean
    private AdvertisementDao advertisementDao;

    @MockBean
    AdvertisementFilter advertisementFilter;

    @MockBean
    MapperFacade mapperFacade;

    @Test
    void testFindAll() {
        given(advertisementDao.findAll()).willReturn(Collections.emptyList());
        assertThat(advertisementService.findAll()).isEqualTo(Collections.emptyList());

        verify(advertisementDao, times(1)).findAll();
    }

    @Test
    void findByIdShouldGetById() {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(1L);

        given(advertisementDao.findById(1L)).willReturn(Optional.of(advertisement));
        AdvertisementDto byId = mapperFacade.map(advertisementService.findById(1L), AdvertisementDto.class);
        assertThat(byId.getId()).isEqualTo(advertisement.getId());

        verify(advertisementDao, times(1)).findById(advertisement.getId());
    }

    @Test
    void findByIdShouldThrowAdvertisementNotFoundException() {
        given(advertisementDao.findById(anyLong())).willThrow(AdvertisementNotFoundException.class);

        Throwable throwable = catchThrowable(() -> advertisementService.findById(anyLong()));
        assertThat(throwable).isInstanceOf(AdvertisementNotFoundException.class);
    }

    @Test
    void saveOrUpdate() {
        AdvertisementDto advertisementDto = new AdvertisementDto();
        advertisementDto.setName("Test");
        advertisementService.saveOrUpdate(mapperFacade.map(advertisementDto, Advertisement.class));
        verify(advertisementDao, times(1))
                .save(any(Advertisement.class));
    }

    @Test
    void deleteById() {
        advertisementService.deleteById(anyLong());
        verify(advertisementDao, times(1)).deleteById(anyLong());
    }

    @Test
    void findAdvertisementByStatusActive() {
        given(advertisementDao.findAdvertisementByStatusActive(true)).willReturn(Collections.emptyList());

        assertThat(advertisementService.findAdvertisementByStatusActive(true)).isEqualTo(Collections.emptyList());

        verify(advertisementDao, times(1)).findAdvertisementByStatusActive(true);
    }
}