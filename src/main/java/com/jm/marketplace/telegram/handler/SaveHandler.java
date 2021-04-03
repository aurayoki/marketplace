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
    private final BotAdvertisementService botAdvertisementService;

    @Autowired
    private BotService botService;

    public SaveHandler(BotAdvertisementService botAdvertisementService) {
        this.botAdvertisementService = botAdvertisementService;
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
        String value = update.getMessage().getText();
        if(botService.getState(chatId).equalsIgnoreCase("ADD_CATEGORY")){
            botAdvertisementService.addCategory(chatId, Long.valueOf(value));
        } else if(botService.getState(chatId).equalsIgnoreCase("ADD_DESCRIPTION")){
            botAdvertisementService.addDescription(chatId, value);
        } else if(botService.getState(chatId).equalsIgnoreCase("ADD_PRICE")){
            botAdvertisementService.addPrice(chatId, Integer.valueOf(value));
        } else if(botService.getState(chatId).equalsIgnoreCase("ADD_NAME")){
            botAdvertisementService.addName(chatId, value);
        } else if(botService.getState(chatId).equalsIgnoreCase("ADD_SUBCATEGORY")){
            botAdvertisementService.addSubcategory(chatId, Long.valueOf(value));
        } else if(botService.getState(chatId).equalsIgnoreCase("ADD_TYPE")){
            botAdvertisementService.addType(chatId, Long.valueOf(value));
        }
        return new Additional(botService, botAdvertisementService).update(update);
    }
}
