package com.jm.marketplace.service.advertisement;

import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.service.general.ReadWriteService;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface AdvertisementService extends ReadWriteService<Advertisement, Long> {

    Page<Advertisement> findAll(Integer page, Map<String, String> params);

    List<Advertisement> findAdvertisementByStatusActive(Boolean field_value);

    Page<Advertisement> findAll(Integer page);

    void saveOrUpdateForTelegramBot(Advertisement advertisement);
}
