package com.jm.marketplace.telegram.model;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.goods.GoodsCategoryService;
import com.jm.marketplace.service.goods.GoodsSubcategoryService;
import com.jm.marketplace.service.goods.GoodsTypeService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public  final class BotAdvertisementService {

    private static HashMap<String, BotAdvertisementDto> statusAdvertisement = new HashMap<>();
    private final AdvertisementService advertisementService;
    private final MapperFacade mapperFacade;
    private final GoodsCategoryService goodsCategoryService;
    private final GoodsSubcategoryService goodsSubcategoryService;
    private final GoodsTypeService goodsTypeService;
    private final UserService userService;

    public BotAdvertisementService(AdvertisementService advertisementService,
                                   MapperFacade mapperFacade,
                                   GoodsCategoryService goodsCategoryService,
                                   GoodsSubcategoryService goodsSubcategoryService,
                                   GoodsTypeService goodsTypeService,
                                   UserService userService) {
        this.advertisementService = advertisementService;
        this.mapperFacade = mapperFacade;
        this.goodsCategoryService = goodsCategoryService;
        this.goodsSubcategoryService = goodsSubcategoryService;
        this.goodsTypeService = goodsTypeService;
        this.userService = userService;
    }

    public  String toString(String chatId) {
        return ("BotAdvertisement{" +  statusAdvertisement.get(chatId).toString()+"}");
    }

    public BotAdvertisementService addName(String chatId, String name) {
        BotAdvertisementDto advertisement = ChooseNotNullAdvertisementDto(chatId);
        advertisement.setName(name);
        statusAdvertisement.put(chatId, advertisement);
        return this;
    }

    public BotAdvertisementService addPrice(String chatId, Integer Price) {
        BotAdvertisementDto advertisement = ChooseNotNullAdvertisementDto(chatId);
        advertisement.setPrice(Price);
        statusAdvertisement.put(chatId, advertisement);
        return this;
    }

    public BotAdvertisementService addType(String chatId, Long idType) {
        BotAdvertisementDto advertisement = ChooseNotNullAdvertisementDto(chatId);
        advertisement.setGoodsType(idType);
        statusAdvertisement.put(chatId, advertisement);
        return this;
    }

    public BotAdvertisementService addCategory(String chatId, Long idCategory) {
        BotAdvertisementDto advertisement = ChooseNotNullAdvertisementDto(chatId);
        advertisement.setGoodsCategory(idCategory);
        statusAdvertisement.put(chatId, advertisement);
        return this;
    }

    public BotAdvertisementService addSubcategory(String chatId, Long idSubcategory) {
        BotAdvertisementDto advertisement = ChooseNotNullAdvertisementDto(chatId);
        advertisement.setGoodsSubcategory(idSubcategory);
        statusAdvertisement.put(chatId, advertisement);
        return this;
    }

    public BotAdvertisementService addDescription(String chatId, String description) {
        BotAdvertisementDto advertisement = ChooseNotNullAdvertisementDto(chatId);
        advertisement.setDescription(description);
        statusAdvertisement.put(chatId, advertisement);
        return this;
    }

    public BotAdvertisementDto getAdvertisement(String chatId) {
        return ChooseNotNullAdvertisementDto(chatId);
    }

    public List<String> getEmptyTextAdvertisement(String chatId) throws NullPointerException, NoSuchElementException {
        BotAdvertisementDto botAdvertisementDto = ChooseNotNullAdvertisementDto(chatId);
        return botAdvertisementDto.emptyTextField();
    }

    public List<String> getEmptyCallbackAdvertisement(String chatId) throws NullPointerException, NoSuchElementException {
        BotAdvertisementDto botAdvertisementDto = ChooseNotNullAdvertisementDto(chatId);
        return botAdvertisementDto.emptyCallbackField();
    }

    private BotAdvertisementDto ChooseNotNullAdvertisementDto(String chatId) {
        BotAdvertisementDto advertisement;
        if(statusAdvertisement.get(chatId) != null) {
            advertisement = statusAdvertisement.get(chatId);
        } else {
            advertisement = new BotAdvertisementDto();
        }
        return advertisement;
    }

    public void save(String chatId) {
        BotAdvertisementDto botAdvertisementDto = ChooseNotNullAdvertisementDto(chatId);
        Advertisement advertisement = new Advertisement();
        advertisement.setName(botAdvertisementDto.getName());
        advertisement.setDescription(botAdvertisementDto.getDescription());
        advertisement.setPrice(botAdvertisementDto.getPrice());
        advertisement.setUser(userService.findById(1L));
        advertisement.setGoodsSubcategory(goodsSubcategoryService.findById(botAdvertisementDto.getGoodsSubcategory()));
        advertisement.setGoodsCategory(goodsCategoryService.findById(botAdvertisementDto.getGoodsSubcategory()));
        advertisement.setGoodsType(goodsTypeService.findById(botAdvertisementDto.getGoodsSubcategory()));
        advertisement.setActive(true);
        advertisement.setBanned(false);
        advertisement.setExpired(false);
        advertisement.setImage("");
        advertisement.setPublication_date(LocalDateTime.now());

        advertisementService.saveOrUpdateForTelegramBot(advertisement);
    }
}
