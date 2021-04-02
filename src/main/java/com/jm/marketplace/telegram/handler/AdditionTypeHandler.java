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
@BotCommand(message = "Тип товара", command = "ADD_TYPE")
public class AdditionTypeHandler implements Handler{
    private final BotService botService;
    private History history = History.create();

    public AdditionTypeHandler(BotService botService) {
        this.botService = botService;
    }


    @Override
    public String toString() {
        return "AdditionTypeHandler{}";
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        String chatId;
        Integer messageId;
        chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        messageId = update.getCallbackQuery().getMessage().getMessageId();
        botService.addType(chatId);
        EditMessageBuilder messageBuilder = EditMessageBuilder.create(chatId, messageId);
        messageBuilder.row();
        messageBuilder.line("Введите тип товара товара: ");
        history.addMessage(update);
        return messageBuilder.build();
    }
}
