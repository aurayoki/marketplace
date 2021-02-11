package com.jm.marketplace.service.advertisement;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dao.AdvertisementDao;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.model.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementDao advertisementDao;
    private final MapperFacade mapperFacade;
    private static final Integer SIZE_PAGE = 4;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementDao advertisementDao, MapperFacade mapperFacade) {
        this.advertisementDao = advertisementDao;
        this.mapperFacade = mapperFacade;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AdvertisementDto> findAll() {
        return mapperFacade.mapAsList(advertisementDao.findAll(), AdvertisementDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdvertisementDto> findAllWithPagination(Integer page) {
        page = getCorrectPage(page);

        Page<Advertisement> advertisements = advertisementDao.findAll(PageRequest.of(page, SIZE_PAGE));
        return advertisements.map(advertisement -> mapperFacade.map(advertisement, AdvertisementDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public AdvertisementDto findById(Long id) {
        return mapperFacade.map(advertisementDao.findById(id).get(), AdvertisementDto.class);
    }

    @Transactional
    @Override
    public void saveOrUpdate(AdvertisementDto advertisementDto) {
        advertisementDao.save(mapperFacade.map(advertisementDto, Advertisement.class));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        advertisementDao.deleteById(id);
    }

    private Integer getCorrectPage(Integer page) {
        if (page == null || page < 1) return 0;
        else return page - 1;
    }
}
