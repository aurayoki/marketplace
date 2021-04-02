package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.EditMessageBuilder;
import com.jm.marketplace.telegram.model.History;
import com.jm.marketplace.telegram.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Service
@BotCommand(message = "Категория товара", command = "ADD_CATEGORY")
@Component
public class AdditionCategoryHandler implements Handler {
    private final BotService botService;
    private History history = History.create();

    @Autowired
    public AdditionCategoryHandler(BotService botService) {
        this.botService = botService;
    }


    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        String chatId;
        Integer messageId;
        chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        botService.addCategory(chatId);
        messageId = update.getCallbackQuery().getMessage().getMessageId();
        EditMessageBuilder messageBuilder = EditMessageBuilder.create(chatId, messageId);
        messageBuilder.row();
        messageBuilder.line("Введите категорию товара: ");
        messageBuilder.line("Тут надо вывести перечень категорий товара. И хорошо бы вводить не только цифры");
        history.addMessage(update);
        return messageBuilder.build();
    }

    @Override
    public String toString() {
        return "AdditionCategoryHandler{}";
    }
}
