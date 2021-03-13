package com.jm.marketplace.telegram.util;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.service.advertisement.AdvertisementService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvertisementUtils {

    private final AdvertisementService advertisementService;

    public AdvertisementUtils(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }


    private Map<AdvertisementDto, Integer> getAdvertisementPages() {

        List<AdvertisementDto> advertisementDtos = filter();
        Map<AdvertisementDto, Integer> advertisementsEachPage = new HashMap<>();
        Integer pageNumber = 1;
        int count = 1;
        for (int i = 0; i < advertisementDtos.size(); i++) {
            advertisementsEachPage.put(advertisementDtos.get(i), pageNumber);
            if((i+1) % ADVERTISEMENTS_IN_PAGE == 0) {
                pageNumber++;
            }
        }
        return advertisementsEachPage;
    }

    private Integer getPageAdvertisementById(Integer id) {
        return getAdvertisementPages().get(advertisementService.findById(Long.valueOf(id)));
    }

    private List<AdvertisementDto> getAdvertisementForCurrentPage(Integer currentPage) {
        List<AdvertisementDto> advertisementDtos = getAdvertisementPages()
                .entrySet()
                .stream()
                .filter(advertisement ->advertisement.getValue() == currentPage)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return advertisementDtos;
    }
}
