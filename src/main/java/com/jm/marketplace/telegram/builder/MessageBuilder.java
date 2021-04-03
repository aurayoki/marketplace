package com.jm.marketplace.telegram.builder;

import com.jm.marketplace.telegram.exception.TelegramBotException;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public final class MessageBuilder {
    @Setter
    private String chatId;
    private final StringBuilder sb = new StringBuilder();
    private final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    private List<InlineKeyboardButton> inlineKeyboardRow = new ArrayList<>();
    private List<KeyboardRow> keyboardRows = new ArrayList<>();


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
        inlineKeyboardRow = new ArrayList<>();
        return this;
    }

    public MessageBuilder button(String text, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        inlineKeyboardRow.add(inlineKeyboardButton);
        return this;
    }

    public MessageBuilder button(List<String> text, List<String> callbackData) throws TelegramBotException {
        if(text.size() != callbackData.size()) {
            throw new TelegramBotException("Button collection size does not match callback collection");
        }

        for(int i = 0; i < text.size(); i++)
        {
            buttonWithArguments(text.get(i), callbackData.get(i));
        }
        return this;
    }

    public MessageBuilder button(List<InlineKeyboardButton> buttons) {
        for (InlineKeyboardButton button : buttons) {
            inlineKeyboardRow.add(button);
        }
        return this;
    }

    public MessageBuilder button(List<String> text, List<String> callbackData, Integer numberRows) throws TelegramBotException {
        if(text.size() != callbackData.size()) {
            throw new TelegramBotException("Button collection size does not match callback collection");
        }
        for(int i = 0; i < text.size(); i++)
        {
            if(i%numberRows == 0) {
                addRowToKeyboard();
                inlineKeyboardRow = new ArrayList<>();
            }
            buttonWithArguments(text.get(i), callbackData.get(i));
        }
        return this;
    }

    public MessageBuilder buttonWithArguments(String text, String callbackData) {
        return button(text, callbackData + " " + text);
    }

    public void keyboardButtons(String... nameKeyboardButtons) {
        KeyboardRow keyboardRow = new KeyboardRow();
        for (String nameKeyboardButton: nameKeyboardButtons) {
            keyboardRow.add(nameKeyboardButton);
        }
        keyboardRows.add(keyboardRow);
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
        if(!keyboardRows.isEmpty()) {
            ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
            replyKeyboard.getSelective();
            replyKeyboard.setResizeKeyboard(true);
            replyKeyboard.setKeyboard(keyboardRows);
            sendMessage.setReplyMarkup(replyKeyboard);
        }

        return sendMessage;
    }

    public void clearKeyboard() {
        keyboard.clear();
    }

    private void addRowToKeyboard() {
        if (inlineKeyboardRow != null) {
            keyboard.add(inlineKeyboardRow);
        }
    }
}
