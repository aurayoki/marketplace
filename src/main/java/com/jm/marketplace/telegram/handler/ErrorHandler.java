package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.MessageBuilder;
import com.jm.marketplace.telegram.model.BotAdvertisement;
import com.jm.marketplace.telegram.model.Page;
import com.jm.marketplace.telegram.service.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Component
@Slf4j
@BotCommand(message = "", command = "ERROR")
public class ErrorHandler implements Handler {
    Page page = Page.create();
    private BotService botService;

    public void setBotService(BotService botService) {
        this.botService = botService;
    }

    public ErrorHandler() {
    }


    @Override
    public String toString() {
        return "ErrorHandler{}";
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
            String chatId = update.getMessage().getChatId().toString();
            MessageBuilder messageBuilder = MessageBuilder.create(update.getMessage().getChatId().toString());
            messageBuilder.clearKeyboard();
            messageBuilder.line("Неизвестная команда.\n для начала работы введите start");
            page.addMessage(update);
            log.error(botService.getState(chatId));
            return messageBuilder.build();

    }
}
