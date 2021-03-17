package com.jm.marketplace.telegram;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.handler.Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateReceiver {

    private final List<Handler> handlers;

    public EditMessageText handle(Update update) {
        try {
            if(isMessageWithText(update)) {
                return getHandler(update
                        .getCallbackQuery()
                        .getMessage()
                        .getText())
                        .update(update);
            } else if(update.hasCallbackQuery()) {
                //return getHandler();
            }

        } catch (UnsupportedOperationException e) {
            log.debug("Command: {} is unsupported", update.toString());
            return null;
        }
    }

    private Handler getHandler(String text) {
        return handlers.stream()
                .filter(h -> h.getClass()
                        .isAnnotationPresent(BotCommand.class))
                .filter(h -> Stream.of(h.getClass()
                        .getAnnotation(BotCommand.class)
                        .command())
                        .anyMatch(c -> c.equalsIgnoreCase(extractCommand(text))))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

    public static String extractCommand(String text) {
        return text.split(" ")[0];
    }
}
