package com.jm.marketplace.service.advertisement;

import com.jm.marketplace.dao.AdvertisementDao;
import com.jm.marketplace.exception.AdvertisementNotFoundException;
import com.jm.marketplace.filter.AdvertisementFilter;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.User;
import com.jm.marketplace.service.general.ReadWriteServiceImpl;
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
import java.util.Optional;

@Service
public class AdvertisementServiceImpl extends ReadWriteServiceImpl<Advertisement, Long> implements AdvertisementService {

    private final AdvertisementDao advertisementDao;
    private AdvertisementFilter advertisementFilter;
    private final UserService userService;
    private static final Integer SIZE_PAGE = 4;

    @Autowired
    public void setAdvertisementFilter(AdvertisementFilter advertisementFilter) {
        this.advertisementFilter = advertisementFilter;
    }

    @Autowired
    public AdvertisementServiceImpl(AdvertisementDao advertisementDao, UserService userService) {
        super(advertisementDao);
        this.advertisementDao = advertisementDao;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Advertisement> findAll(Integer page) {
        return findAll(page, new HashMap<>());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Advertisement> findAll(Integer page, Map<String, String> params) {
        page = getCorrectPage(page);
        return advertisementDao.findAll(advertisementFilter.getSpecification(params), PageRequest.of(page, SIZE_PAGE));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Advertisement> findById(Long id) {
        return Optional.ofNullable(advertisementDao.findById(id).orElseThrow(() ->
                new AdvertisementNotFoundException(String.format("Advertisement not found by id: %s", id))));
    }

    @Transactional
    @Override
    public void saveOrUpdate(Advertisement advertisement) {
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        advertisement.setUser(user);
        advertisementDao.save(advertisement);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Advertisement> findAdvertisementByStatusActive(Boolean active) {
        return advertisementDao.findAdvertisementByStatusActive(active);
    }

    private Integer getCorrectPage(Integer page) {
        if (page == null || page < 1) return 0;
        else return page - 1;
    }
}
