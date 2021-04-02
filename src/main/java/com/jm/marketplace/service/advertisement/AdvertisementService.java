package com.jm.marketplace.service.advertisement;

import com.jm.marketplace.dao.AdvertisementDao;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.model.Advertisement;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface AdvertisementService {

    List<Advertisement> findAll();

    Page<Advertisement> findAll(Integer page);

    Page<Advertisement> findAll(Integer page, Map<String, String> params);

    Advertisement findById(Long id);

    void saveOrUpdate(Advertisement advertisement);

    void deleteById(Long id);

    List<Advertisement> findAdvertisementByStatusActive(Boolean field_value);

    void saveOrUpdateForTelegramBot(Advertisement advertisement);
}
