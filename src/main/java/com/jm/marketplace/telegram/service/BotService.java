package com.jm.marketplace.telegram.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotService {
    void start(final String chatId);

    void save(final String chatId);

    void showAdvertisement(final String chatId);

    void showPageAdvertisement(final String chatId);

    void addAdvertisement(final String chatId);

    void addCategory(final String chatId);

    void addSubcategory(final String chatId);

    void addType(final String chatId);

    void addPrice(final String chatId);

    void addName(final String chatId);

    void addDescription(final String chatId);

    void back(final String chatId);

    void next(final String chatId);

    String getState(String chatId);
}
