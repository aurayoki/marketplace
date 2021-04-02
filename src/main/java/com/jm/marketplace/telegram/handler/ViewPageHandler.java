package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.EditMessageBuilder;
import com.jm.marketplace.telegram.builder.MessageBuilder;
import com.jm.marketplace.telegram.exception.TelegramBotException;
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
@BotCommand(command = "PAGE", message = "Показать объявления")
public class ViewPageHandler implements Handler {

    private final AdvertisementService advertisementService;
    private final AdvertisementUtils advertisementUtils;
    private final BotService botService;
    private History history = History.create();;

    @Autowired
    public ViewPageHandler(AdvertisementService advertisementService, AdvertisementUtils advertisementUtils, BotService botService) {
        this.advertisementService = advertisementService;
        this.advertisementUtils = advertisementUtils;
        this.botService = botService;
    }


    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        try {
            Integer currentPage;
            BotApiMethod<? extends Serializable> message;

            try {
                currentPage = Integer.valueOf(update.getCallbackQuery().getData().split(" ")[1]);
            } catch (Exception classCastException) {
                currentPage = 1;
            }
            if(update.hasCallbackQuery()) {
                String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
                Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                EditMessageBuilder messageBuilder = EditMessageBuilder.create(chatId, messageId);
                botService.showPageAdvertisement(chatId);
                messageBuilder.row();
                messageBuilder.button(advertisementUtils.getInlineKeyboardButtonsAdvertisementForCurrentPage(currentPage));
                messageBuilder.row();
                messageBuilder.button(advertisementUtils.getInlineKeyboardButtonPagination());
                messageBuilder.line(advertisementUtils.getAdvertisementTextForCurrentPage(currentPage));
                history.addMessage(update);
                return messageBuilder.build();
            } else if(update.hasMessage()) {
                String chatId = update.getMessage().getChatId().toString();
                Integer messageId = update.getMessage().getMessageId();
                MessageBuilder messageBuilder = MessageBuilder.create(chatId);
                messageBuilder.row();
                messageBuilder.button(advertisementUtils.getInlineKeyboardButtonsAdvertisementForCurrentPage(currentPage));
                messageBuilder.row();
                messageBuilder.button(advertisementUtils.getInlineKeyboardButtonPagination());
                messageBuilder.line(advertisementUtils.getAdvertisementTextForCurrentPage(currentPage));
                history.addMessage(update);
                return messageBuilder.build();
            }
            throw new TelegramBotException("Как такое может быть?");

        }
        catch (Exception e) {
            e.getStackTrace();
            return new ErrorHandler().update(update);
        }
    }

    @Override
    public String toString() {
        return "ViewPageHandler{}";
    }
}
