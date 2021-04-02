package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.model.BotAdvertisementService;
import com.jm.marketplace.telegram.service.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Component
@Slf4j
@BotCommand(message = "", command = "SAVE")
public class SaveHandler implements Handler{
    @Autowired
    private BotService botService;

    public SaveHandler() {
    }

    @Autowired
    public void setBotService(BotService botService) {
        this.botService = botService;
    }

    @Override
    public String toString() {
        return "SaveHandler{}";
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        BotAdvertisementService botAdvertisementService = BotAdvertisementService.create();
        String value = update.getMessage().getText();
        if(botService.getState(chatId).equalsIgnoreCase("ADD_CATEGORY")){
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            botAdvertisementService.addCategory(chatId, Long.valueOf(value));
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
        }
        else if(botService.getState(chatId).equalsIgnoreCase("ADD_DESCRIPTION")){

            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            botAdvertisementService.addDescription(chatId, value);
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            log.error(update.getMessage().getText());
        } else if(botService.getState(chatId).equalsIgnoreCase("ADD_PRICE")){

            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            botAdvertisementService.addPrice(chatId, Integer.valueOf(value));
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            log.error(update.getMessage().getText());
        } else if(botService.getState(chatId).equalsIgnoreCase("ADD_NAME")){
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            botAdvertisementService.addName(chatId, value);
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            log.error(update.getMessage().getText());
        } else if(botService.getState(chatId).equalsIgnoreCase("ADD_SUBCATEGORY")){
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            botAdvertisementService.addSubcategory(chatId, Long.valueOf(value));
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            log.error(update.getMessage().getText());
        } else if(botService.getState(chatId).equalsIgnoreCase("ADD_TYPE")){
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            botAdvertisementService.addType(chatId, Long.valueOf(value));
            log.error(botAdvertisementService.getAdvertisement(chatId).toString());
            log.error(update.getMessage().getText());
        }
        return new Additional(botService).update(update);
    }
}
