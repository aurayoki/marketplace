package com.jm.marketplace.telegram;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.handler.ErrorHandler;
import com.jm.marketplace.telegram.handler.Handler;
import com.jm.marketplace.telegram.handler.SaveHandler;
import com.jm.marketplace.telegram.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateReceiver {

    private final List<Handler> handlers;
    private final String handlerSaveName = "ADD_NAME ADD_CATEGORY ADD_SUBCATEGORY ADD_TYPE ADD_DESCRIPTION ADD_PRICE";
    private final BotService botService;

    public BotApiMethod<? extends Serializable> handle(Update update) {
        try {
            if(isMessageWithText(update)) {
                return getHandler(update
                        .getMessage()
                        .getText(), update.getMessage().getChatId().toString())
                        .update(update);

            } else if(update.hasCallbackQuery()) {
                return getHandler(update
                        .getCallbackQuery()
                        .getData(), update.getCallbackQuery().getMessage().getChatId().toString())
                        .update(update);
            }
            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            log.debug("Command: {} is unsupported", update.toString());
            return null;
        }
    }

    private Handler getHandler(String text, String chatId) {
        Handler handler;
        handler = handlers.stream()
                .filter(h -> h.getClass()
                        .isAnnotationPresent(BotCommand.class))
                .filter(h -> Stream.of(h.getClass()
                        .getAnnotation(BotCommand.class)
                        .command())
                        .anyMatch(c -> c.equalsIgnoreCase(text.split(" ")[0])))
                .findAny()
                .orElse(handlers.stream()
                        .filter(h -> h.getClass()
                                .isAnnotationPresent(BotCommand.class))
                        .filter(h -> Stream.of(h.getClass()
                                .getAnnotation(BotCommand.class)
                                .message())
                                .anyMatch(c -> c.equalsIgnoreCase(text)))
                        .findAny().orElse(new ErrorHandler()));
            if (handlerSaveName.contains(botService.getState(chatId)) && handler.getClass() != SaveHandler.class) {
                handler = getHandler("SAVE", chatId);
        }
            return handler;
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }
}
