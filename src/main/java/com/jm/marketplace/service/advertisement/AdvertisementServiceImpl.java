package com.jm.marketplace.service.advertisement;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dao.AdvertisementDao;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.exception.AdvertisementNotFoundException;
import com.jm.marketplace.filter.AdvertisementFilter;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementDao advertisementDao;
    private final MapperFacade mapperFacade;
    private AdvertisementFilter advertisementFilter;
    private final UserService userService;
    private static final Integer SIZE_PAGE = 4;

    @Autowired
    public void setAdvertisementFilter(AdvertisementFilter advertisementFilter) {
        this.advertisementFilter = advertisementFilter;
    }

    @Autowired
    public AdvertisementServiceImpl(AdvertisementDao advertisementDao, MapperFacade mapperFacade, UserService userService) {
        this.advertisementDao = advertisementDao;
        this.mapperFacade = mapperFacade;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AdvertisementDto> findAll() {
        return mapperFacade.mapAsList(advertisementDao.findAll(), AdvertisementDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdvertisementDto> findAll(Integer page) {
        return findAll(page, new HashMap<>());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdvertisementDto> findAll(Integer page, Map<String, String> params) {
        page = getCorrectPage(page);
        Page<Advertisement> advertisements = advertisementDao.findAll(advertisementFilter.getSpecification(params), PageRequest.of(page, SIZE_PAGE));
        return advertisements.map(advertisement -> mapperFacade.map(advertisement, AdvertisementDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public AdvertisementDto findById(Long id) {
        Advertisement advertisement = advertisementDao.findById(id).orElseThrow(() ->
                new AdvertisementNotFoundException(String.format("Advertisement not found by id: %s", id)));
        return mapperFacade.map(advertisement, AdvertisementDto.class);
    }

    @Transactional
    @Override
    public void saveOrUpdate(AdvertisementDto advertisementDto) {
        UserDto userDto = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        advertisementDto.setUser(userDto);
        advertisementDao.save(mapperFacade.map(advertisementDto, Advertisement.class));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        advertisementDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AdvertisementDto> findAdvertisementByStatusActive(Boolean active) {
        return mapperFacade.mapAsList(advertisementDao.findAdvertisementByStatusActive(active), AdvertisementDto.class);
    }

    private Integer getCorrectPage(Integer page) {
        if (page == null || page < 1) return 0;
        else return page - 1;
    }
}
