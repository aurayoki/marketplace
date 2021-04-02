package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.EditMessageBuilder;
import com.jm.marketplace.telegram.builder.MessageBuilder;
import com.jm.marketplace.telegram.model.History;
import com.jm.marketplace.telegram.service.BotService;
import com.jm.marketplace.telegram.util.AdvertisementUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Component
@Slf4j
@BotCommand(command = "ADV", message = "")
public class ViewAdvertisementHandler implements Handler {

    private final AdvertisementUtils advertisementUtils;
    private final AdvertisementService advertisementService;
    private final BotService botService;
    private History history = History.create();;

    @Autowired
    public ViewAdvertisementHandler(AdvertisementUtils advertisementUtils,
                                    AdvertisementService advertisementService, BotService botService) {
        this.advertisementUtils = advertisementUtils;
        this.advertisementService = advertisementService;
        this.botService = botService;
    }

    @Override
    public String toString() {
        return "ViewAdvertisementHandler{}";
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        int currentAdv;
        try {
            String chatId;
            Integer messageId;
            if(update.hasCallbackQuery()) {
                chatId = update.getCallbackQuery().getMessage().getChatId().toString();
                messageId = update.getCallbackQuery().getMessage().getMessageId();
                currentAdv = Integer.valueOf(update.getCallbackQuery().getData().split(" ")[1]);
                EditMessageBuilder messageBuilder = EditMessageBuilder.create(chatId, messageId);
                botService.showAdvertisement(chatId);
                messageBuilder.row();
                messageBuilder.button("Назад", "back");
                messageBuilder.line(advertisementUtils.getAdvertisemenTextById(currentAdv));
                history.addMessage(update);
                log.error(botService.getState(chatId));
              return messageBuilder.build();
            } else {
                chatId = update.getCallbackQuery().getMessage().getChatId().toString();
                currentAdv = Integer.valueOf(update.getMessage().getText().split(" ")[1]);
                MessageBuilder messageBuilder = MessageBuilder.create(chatId);
                botService.showAdvertisement(chatId);
                messageBuilder.row();
                messageBuilder.button("Назад", "back");
                messageBuilder.line(advertisementUtils.getAdvertisemenTextById(currentAdv));
                history.addMessage(update);
                log.error(botService.getState(chatId));
                return messageBuilder.build();
            }
        } catch(NullPointerException nullPointerException) {
            log.error("\nnullPointerException: "+ nullPointerException.getStackTrace());
        } catch (NumberFormatException numberFormatException) {
            log.error("\nNumber format exception: " + numberFormatException.getStackTrace());
        }
           return null;
    }
}
