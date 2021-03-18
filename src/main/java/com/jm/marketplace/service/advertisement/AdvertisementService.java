package com.jm.marketplace.service.advertisement;

import com.jm.marketplace.dao.AdvertisementDao;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.service.general.ReadWriteService;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface AdvertisementService<T, K> extends ReadWriteService<T, K> {

    Page<Advertisement> findAll(Integer page, Map<String, String> params);

    List<Advertisement> findAdvertisementByStatusActive(Boolean field_value);

    Page<Advertisement> findAll(Integer page);

//    Advertisement findById(Long id);
//    List<Advertisement> findAll();

//    void saveOrUpdate(Advertisement advertisement);

//    void deleteById(Long id);
}
