package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@BotCommand(message = "", command = "SAVE")
public class SaveHandler implements Handler{
    
    @Override
    public EditMessageText update(Update message) {
        return null;
    }
}
