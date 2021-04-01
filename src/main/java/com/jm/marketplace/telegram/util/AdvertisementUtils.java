package com.jm.marketplace.telegram.util;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
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

    public Integer getPageAdvertisementById(Long id) {
        return getAdvertisementPages().get(advertisementService.findById(id));
    }

    public List<AdvertisementDto> getAdvertisementForCurrentPage(int currentPage) {
        List<AdvertisementDto> advertisementDtos = getAdvertisementPages()
                .entrySet()
                .stream()
                .filter(advertisement ->advertisement.getValue() == currentPage)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return advertisementDtos;
    }

    public String getAdvertisemenTextById(long id) {
        AdvertisementDto advertisementDto = advertisementService.findById(id);
        StringBuilder sb = new StringBuilder();
        sb.append(advertisementDto.getName()).append("\n");
        sb.append(advertisementDto.getPrice()).append("\n");
        sb.append(advertisementDto.getDescription()).append("\n");
        sb.append(advertisementDto.getUser()).append("\n");
        return sb.toString();
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


    public InlineKeyboardMarkup getInlineButtonsPagination(int currentPage) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getInlineKeyboardButtonsAdvertisementForCurrentPage(currentPage));
        rowList.add(getInlineKeyboardButtonPagination());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }


    public List<InlineKeyboardButton> getInlineKeyboardButtonsAdvertisementForCurrentPage(int currentPage) {
        List<AdvertisementDto> advertisementDtos = getAdvertisementForCurrentPage(currentPage);
        return getInlineKeyboardButtons(advertisementDtos);
    }

    public static List<InlineKeyboardButton> getInlineKeyboardButtons(List<AdvertisementDto> advertisementDtos) {
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        for(AdvertisementDto advertisementDto : advertisementDtos) {
            InlineKeyboardButton advertisementButton = new InlineKeyboardButton();
            advertisementButton.setText(advertisementDto.getName());
            advertisementButton.setCallbackData("ADV " + advertisementDto.getId());
            keyboardButtonsRow.add(advertisementButton);
        }

        return keyboardButtonsRow;
    }

}
