package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.EditMessageBuilder;
import com.jm.marketplace.telegram.builder.MessageBuilder;
import com.jm.marketplace.telegram.model.History;
import com.jm.marketplace.telegram.util.AdvertisementUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Component
@Slf4j
@BotCommand(command = "MAP", message = "")
public class MapHandler implements Handler{
    private final AdvertisementUtils advertisementUtils;
    private History history = History.create();;

    public MapHandler(AdvertisementUtils advertisementUtils) {
        this.advertisementUtils = advertisementUtils;
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        try {
            if(update.hasCallbackQuery()) {
                String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
                Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                EditMessageBuilder messageBuilder = EditMessageBuilder.create(chatId, messageId);
                messageBuilder.line("Тут текст");
                messageBuilder.row();
                messageBuilder.button("Назад","BACK");
                return messageBuilder.build();
            } else {
                String chatId = update.getMessage().getChatId().toString();
                MessageBuilder messageBuilder = MessageBuilder.create(chatId);
                messageBuilder.line("Тут текст");
                messageBuilder.row();
                messageBuilder.button("Назад","BACK");
                return messageBuilder.build();
            }
        } catch(Exception e) {
            log.error(e.getStackTrace().toString());
            log.error(e.getMessage().toString());
        }
        return new ErrorHandler().update(update);
    }
}
