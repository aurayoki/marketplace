package com.jm.marketplace.config.quartzconfig;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@NoArgsConstructor
public class RemovalOfAnAdvertisementFromPublication extends QuartzJobBean {

    private AdvertisementService<Advertisement, Long> advertisementService;
    private MapperFacade mapperFacade;

    @Autowired
    public RemovalOfAnAdvertisementFromPublication(AdvertisementService<Advertisement, Long> advertisementService, MapperFacade mapperFacade) {
        this.advertisementService = advertisementService;
        this.mapperFacade = mapperFacade;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<AdvertisementDto> advertisementDtoList = mapperFacade.mapAsList(advertisementService.findAdvertisementByStatusActive(true), AdvertisementDto.class)
                .stream().filter(advertisement -> isFilterDateForStream(advertisement, 30))
                .collect(Collectors.toList());

        log.info("Запущена проверка объявлений");
        if (!advertisementDtoList.isEmpty()) {
            advertisementDtoList.forEach(advertisementDto -> {
                advertisementDto.setActive(false);
                advertisementDto.setExpired(false);
                advertisementService.saveOrUpdate(mapperFacade.map(advertisementDto, Advertisement.class));
            });

        }
    }

    private boolean isFilterDateForStream(AdvertisementDto advertisementDto, long days) {
        LocalDateTime publication_date = advertisementDto.getPublication_date();
        long between = ChronoUnit.DAYS.between(publication_date, LocalDateTime.now());
        return between >= days;
    }
}
