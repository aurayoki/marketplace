package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;

@BotCommand(message = "", command = "ERROR")
public class ErrorHandler implements Handler {
    @Override
    public List<EditMessageText> update(String message) {
        return null;
    }
}
