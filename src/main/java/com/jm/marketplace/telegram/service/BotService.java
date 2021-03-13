package com.jm.marketplace.telegram.service;

public interface BotService {
    void save();

    void showAdvertisement(Long id);

    void showAllAdvertisement();

    void addAdvertisement(Long idCategory, Long idSubcategory, Long idType, String description);

    void addCategory(Long id);

    void addSubcategory(Long id);

    void addType(Long id);
}
