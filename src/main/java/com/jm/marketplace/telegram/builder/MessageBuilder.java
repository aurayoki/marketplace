package com.jm.marketplace.telegram.builder;

import com.jm.marketplace.model.User;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;

public final class MessageBuilder {
    @Setter
    private String chatId;
    private final StringBuilder sb = new StringBuilder();
    private final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    private List<InlineKeyboardButton> row = null;


    private MessageBuilder() {
    }

    public static MessageBuilder create(String chatId) {
        MessageBuilder builder = new MessageBuilder();
        builder.setChatId(chatId);
        return builder;
    }

    public MessageBuilder line(String text, Object... args) {
        sb.append(String.format(text, args));
        return line();
    }

    public MessageBuilder line() {
        sb.append(String.format("%n"));
        return this;
    }


    public MessageBuilder row() {
        addRowToKeyboard();
        row = new ArrayList<>();
        return this;
    }

    public MessageBuilder button(String text, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        row.add(inlineKeyboardButton);
        return this;
    }


    public MessageBuilder buttonWithArguments(String text, String callbackData) {
        return button(text, callbackData + " " + text);
    }


    public SendMessage build() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        sendMessage.setText(sb.toString());

        addRowToKeyboard();

        if(!keyboard.isEmpty()) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }

        return sendMessage;
    }

    /**
     * Добавляет новые строки, если они не пусты
     */
    private void addRowToKeyboard() {
        if (row != null) {
            keyboard.add(row);
        }
    }
}
