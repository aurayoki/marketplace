package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.EditMessageBuilder;
import com.jm.marketplace.telegram.model.History;
import com.jm.marketplace.telegram.service.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Component
@Slf4j
@BotCommand(message = "Имя товара", command = {"ADD_NAME"})
public class AdditionNameHandler implements Handler{
    private final BotService botService;
    private History history = History.create();

    public AdditionNameHandler(BotService botService) {
        this.botService = botService;
    }


    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        String chatId;
        Integer messageId;
        chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        messageId = update.getCallbackQuery().getMessage().getMessageId();
        botService.addName(chatId);
        EditMessageBuilder messageBuilder = EditMessageBuilder.create(chatId, messageId);
        messageBuilder.row();
        messageBuilder.line("Введите название товара товара: ");
        history.addMessage(update);
        return messageBuilder.build();
    }

    @Override
    public String toString() {
        return "AdditionNameHandler{}";
    }
}
