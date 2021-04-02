package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.model.History;
import com.jm.marketplace.telegram.service.BotService;
import com.jm.marketplace.telegram.util.AdvertisementUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
@BotCommand(message = "", command = "BACK")
public class Back implements Handler {
    private final BotService botService;
    private final AdvertisementUtils advertisementUtils;
    private final List<Handler> handlers;
    private History history = History.create();;

    @Autowired
    public Back(BotService botService, AdvertisementUtils advertisementUtils, List<Handler> handlers) {
        this.botService = botService;
        this.advertisementUtils = advertisementUtils;
        this.handlers = handlers;
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        botService.back(chatId);
        String messageId = update.getCallbackQuery().getMessage().getMessageId().toString();
        update = History.getLastUpdate(chatId, messageId);

        return getHandler(update).update(update);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Handler getHandler(Update update) {
        return handlers.stream()
                .filter(h -> h.getClass()
                        .isAnnotationPresent(BotCommand.class))
                .filter(h -> Stream.of(h.getClass()
                        .getAnnotation(BotCommand.class)
                        .command())
                        .anyMatch(c -> c.equalsIgnoreCase(update
                                .getCallbackQuery()
                                .getData()
                                .split(" ")[0])))
                .findAny().orElse(new StartHandler(botService));
    }
}
