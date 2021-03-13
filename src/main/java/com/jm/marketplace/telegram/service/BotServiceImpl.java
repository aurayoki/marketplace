package com.jm.marketplace.telegram.service;

import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.goods.GoodsCategoryService;
import com.jm.marketplace.service.goods.GoodsSubcategoryService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class BotServiceImpl implements BotService{
    private final AdvertisementService advertisementService;
    private final GoodsCategoryService goodsCategoryService;
    private final GoodsSubcategoryService goodsSubcategoryService;
    private final UserService userService;


    @Autowired
    public BotServiceImpl(AdvertisementService advertisementService,
                          GoodsCategoryService goodsCategoryService,
                          GoodsSubcategoryService goodsSubcategoryService,
                          UserService userService) {
        this.advertisementService = advertisementService;
        this.goodsCategoryService = goodsCategoryService;
        this.goodsSubcategoryService = goodsSubcategoryService;
        this.userService = userService;
    }

    @Override
    public void save() {

    }

    @Override
    public void showAdvertisement(Long id) {

    }

    @Override
    public void showAllAdvertisement() {

    }

    @Override
    public void addAdvertisement() {

    }

    @Override
    public void addCategory(Long id) {

    }

    @Override
    public void addSubcategory(Long id) {

    }

    @Override
    public void addType(Long id) {

    }
}
