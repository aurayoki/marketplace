package com.jm.marketplace.telegram.util;


import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.User;
import com.jm.marketplace.model.geoStructure.Point;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.util.geo.GeoCoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AdvertisementUtils {
    private int ADVERTISEMENTS_IN_PAGE = 3;
    private final AdvertisementService advertisementService;
    private final GeoCoderService geoCoderService;

    @Autowired
    public AdvertisementUtils(AdvertisementService advertisementService, GeoCoderService geoCoderService) {
        this.advertisementService = advertisementService;
        this.geoCoderService = geoCoderService;
    }

    public void setAdvertisementInPage(int count) {
        ADVERTISEMENTS_IN_PAGE = count;
    }

    public Map<Advertisement, Integer> getAdvertisementPages() {

        List<Advertisement> advertisements = advertisementService.findAll();
        Map<Advertisement, Integer> advertisementsEachPage = new HashMap<>();
        Integer pageNumber = 1;
        int count = 1;
        for (int i = 0; i < advertisements.size(); i++) {
            advertisementsEachPage.put(advertisements.get(i), pageNumber);
            if((i+1) % ADVERTISEMENTS_IN_PAGE == 0) {
                pageNumber++;
            }
        }
        return advertisementsEachPage;
    }

    public Integer getPageAdvertisementById(Long id) {
        return getAdvertisementPages().get(advertisementService.findById(id));
    }

    public List<Advertisement> getAdvertisementForCurrentPage(int currentPage) {
        List<Advertisement> advertisements = getAdvertisementPages()
                .entrySet()
                .stream()
                .filter(advertisement ->advertisement.getValue() == currentPage)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return advertisements;
    }

    public String getAdvertisemenTextById(long id) {
        Advertisement advertisement = advertisementService.findById(id).get();
        StringBuilder sb = new StringBuilder();
        sb.append(advertisement.getName()).append("\n");
        sb.append(advertisement.getPrice()).append("\n");
        sb.append(advertisement.getDescription()).append("\n");
        sb.append(advertisement.getUser()).append("\n");
        return sb.toString();
    }

    public String getAdvertisementTextForCurrentPage(int currentPage) {

        List<Advertisement> advertisements = getAdvertisementForCurrentPage(currentPage);

        StringBuilder sb = new StringBuilder();

        for (Advertisement advertisement : advertisements) {
            sb.append(advertisement.getName()).append("\n");
            sb.append(advertisement.getPrice()).append("\n");
            sb.append(advertisement.getDescription()).append("\n");
            sb.append(advertisement.getUser()).append("\n");
            sb.append("-------------------");
            sb.append("\n");
        }

        return sb.toString();

    }

    public Point getInfoLocationByCoordinates(Integer advertisementId) {
        return geoCoderService.getCoordinatesByAddress(Objects.requireNonNull(advertisementService.findById(advertisementId.longValue())
                .orElse(null))
                .getUser()
                .getCity()
                .getName());
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
        List<Advertisement> advertisements = advertisementService.findAll();
        int pagesCount = (int) Math.ceil(advertisements.size() / (double) ADVERTISEMENTS_IN_PAGE);
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
        List<Advertisement> advertisements = getAdvertisementForCurrentPage(currentPage);
        return getInlineKeyboardButtons(advertisements);
    }

    public static List<InlineKeyboardButton> getInlineKeyboardButtons(List<Advertisement> advertisements) {
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        for(Advertisement advertisement : advertisements) {
            InlineKeyboardButton advertisementButton = new InlineKeyboardButton();
            advertisementButton.setText(advertisement.getName());
            advertisementButton.setCallbackData("ADV " + advertisement.getId());
            keyboardButtonsRow.add(advertisementButton);
        }

        return keyboardButtonsRow;
    }

    public String getSellerInfoByIdAdvertisement(Long idAdvertisement) {


        return "";
    }

}
