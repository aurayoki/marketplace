package com.jm.marketplace.telegram.util;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvertisementUtils {
    private int ADVERTISEMENTS_IN_PAGE = 3;
    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementUtils(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    public void setAdvertisementInPage(int count) {
        ADVERTISEMENTS_IN_PAGE = count;
    }

    public Map<AdvertisementDto, Integer> getAdvertisementPages() {

        List<AdvertisementDto> advertisementDtos = advertisementService.findAll();
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

    public Integer getPageAdvertisementById(Integer id) {
        return getAdvertisementPages().get(advertisementService.findById(Long.valueOf(id)));
    }

    public List<AdvertisementDto> getAdvertisementForCurrentPage(Integer currentPage) {
        List<AdvertisementDto> advertisementDtos = getAdvertisementPages()
                .entrySet()
                .stream()
                .filter(advertisement ->advertisement.getValue() == currentPage)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return advertisementDtos;
    }

    public String getAdvertisementTextForCurrentPage(int currentPage) {

        List<AdvertisementDto> advertisementDtos = getAdvertisementForCurrentPage(currentPage);

        StringBuilder sb = new StringBuilder();

        for (AdvertisementDto advertisementDto : advertisementDtos) {
            sb.append(advertisementDto.getName()).append("\n");
            sb.append(advertisementDto.getPrice()).append("\n");
            sb.append(advertisementDto.getDescription()).append("\n");
            sb.append(advertisementDto.getUser()).append("\n");
            sb.append("-------------------");
            sb.append("\n");
        }

        return sb.toString();

    }

    public int getCountPages() {
        int advertisementSize = advertisementService.findAll().size();
        int countPages = advertisementSize/ADVERTISEMENTS_IN_PAGE;
        if(advertisementSize%ADVERTISEMENTS_IN_PAGE!=0)
        {
            countPages++;
        }
        return countPages;
    }

    public List<InlineKeyboardButton> getInlineKeyboardButtonPagination() {
        List<AdvertisementDto> advertisementDtos = advertisementService.findAll();
        int pagesCount = (int) Math.ceil(advertisementDtos.size() / (double) ADVERTISEMENTS_IN_PAGE);
        List<InlineKeyboardButton> keyboardButtonsRowPageNamber = new ArrayList<>();

        for (int i = 1; i <= pagesCount; i++) {
            InlineKeyboardButton pageNumberButton = new InlineKeyboardButton();
            pageNumberButton.setText(String.valueOf(i));
            pageNumberButton.setCallbackData("PAGE " + (i));
            keyboardButtonsRowPageNamber.add(pageNumberButton);
        }
        return keyboardButtonsRowPageNamber;
    }
}
