package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.EditMessageBuilder;
import com.jm.marketplace.telegram.model.Page;
import com.jm.marketplace.telegram.service.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Component
@Slf4j
@BotCommand(message = "Описание товара", command = "ADD_DESCRIPTION")
public class AdditionDescriptionsHandler implements Handler{
    private final BotService botService;
    private Page page = Page.create();

    public AdditionDescriptionsHandler(BotService botService) {
        this.botService = botService;
    }

    @Override
    public String toString() {
        return "AdditionDescriptionsHandler{}";
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        String chatId;
        Integer messageId;
        chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        messageId = update.getCallbackQuery().getMessage().getMessageId();
        botService.addDescription(chatId);
        EditMessageBuilder messageBuilder = EditMessageBuilder.create(chatId, messageId);
        messageBuilder.row();
        messageBuilder.line("Введите описание товара товара: ");
        page.addMessage(update);
        return messageBuilder.build();
    }
}
