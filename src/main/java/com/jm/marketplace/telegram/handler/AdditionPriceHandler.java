package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.EditMessageBuilder;
import com.jm.marketplace.telegram.model.History;
import com.jm.marketplace.telegram.service.BotService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Service
@BotCommand(message = "Цена товара", command = "ADD_PRICE")
@Component
public class AdditionPriceHandler implements Handler{
    private final BotService botService;
    private History history = History.create();

    public AdditionPriceHandler(BotService botService) {
        this.botService = botService;
    }


    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        String chatId;
        Integer messageId;
        chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        messageId = update.getCallbackQuery().getMessage().getMessageId();
        botService.addPrice(chatId);
        EditMessageBuilder messageBuilder = EditMessageBuilder.create(chatId, messageId);
        messageBuilder.row();
        messageBuilder.line("Введите цену товара товара: ");
        history.addMessage(update);
        return messageBuilder.build();
    }

    @Override
    public String toString() {
        return "AdditionPriceHandler{}";
    }
}