package com.jm.marketplace.config.quartzconfig;

import com.jm.marketplace.dto.goods.AdvertisementDto;
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

    private AdvertisementService advertisementService;

    @Autowired
    public RemovalOfAnAdvertisementFromPublication(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<AdvertisementDto> advertisementDtoList = advertisementService.findAdvertisementByStatusActive(true)
                .stream().filter(advertisementDto -> isFilterDateForStream(advertisementDto, 30)).collect(Collectors.toList());

        log.info("Запущена проверка объявлений");
        if (!advertisementDtoList.isEmpty()) {
            advertisementDtoList.forEach(advertisementDto -> {
                advertisementDto.setActive(false);
                advertisementDto.setExpired(false);
                advertisementService.saveOrUpdate(advertisementDto);
            });

        }
    }

    private boolean isFilterDateForStream(AdvertisementDto advertisementDto, long days) {
        LocalDateTime publication_date = advertisementDto.getPublication_date();
        long between = ChronoUnit.DAYS.between(publication_date, LocalDateTime.now());
        return between >= days;
    }
}
