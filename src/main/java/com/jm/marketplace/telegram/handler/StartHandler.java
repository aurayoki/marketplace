package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.MessageBuilder;
import com.jm.marketplace.telegram.model.History;
import com.jm.marketplace.telegram.service.BotService;
import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Component
@Slf4j
@BotCommand(command = "/START", message = "start")
public class StartHandler implements Handler{

    private final BotService botService;
    private History history = History.create();

    @Autowired
    StateMachineFactory<States, Event> stateMachineFactory;

    @Autowired
    public StartHandler(BotService botService) {
        this.botService = botService;
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        try {
            String chatId = update.getMessage().getChatId().toString();
            MessageBuilder messageBuilder = MessageBuilder.create(update.getMessage().getChatId().toString());
            messageBuilder.row();
            messageBuilder.clearKeyboard();
            messageBuilder.keyboardButtons("Показать объявления", "Добавить товар");
            messageBuilder.line("Приветсвую");
            history.addMessage(update);
            log.error(botService.getState(chatId));
            return messageBuilder.build();
        }
        catch (Exception e) {
            e.getStackTrace();
            return new ErrorHandler().update(update);
        }
    }

    @Override
    public String toString() {
        return "StartHandler{}";
    }
}
