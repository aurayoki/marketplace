package com.jm.marketplace.telegram.builder;

import com.jm.marketplace.telegram.exception.TelegramBotException;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class EditMessageBuilder {
    @Setter
    private String chatId;
    @Setter
    private Integer messageId = null;
    private EditMessageText editMessageText;
    private final StringBuilder sb = new StringBuilder();
    private final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    private List<InlineKeyboardButton> inlineKeyboardRow = null;
    private List<KeyboardRow> keyboardRows = new ArrayList<>();


    private EditMessageBuilder() {
    }

    public static EditMessageBuilder create(String chatId, Integer messageId) {
        EditMessageBuilder builder = new EditMessageBuilder();
        builder.setChatId(chatId);
        builder.setMessageId(messageId);
        return builder;
    }


    public EditMessageBuilder line(String text, Object... args) {
        sb.append(String.format(text, args));
        return line();
    }

    public EditMessageBuilder line() {
        sb.append(String.format("%n"));
        return this;
    }


    public EditMessageBuilder row() {
        addRowToKeyboard();
        inlineKeyboardRow = new ArrayList<>();
        return this;
    }

    public EditMessageBuilder button(String text, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        inlineKeyboardRow.add(inlineKeyboardButton);
        return this;
    }

    public EditMessageBuilder button(List<InlineKeyboardButton> buttons) {
        for (InlineKeyboardButton button : buttons) {
            inlineKeyboardRow.add(button);
        }
        return this;
    }

    public EditMessageBuilder button(List<String> text, List<String> callbackData) throws TelegramBotException {
        if (text.size() != callbackData.size()) {
            throw new TelegramBotException("Button collection size does not match callback collection");
        }

        for (int i = 0; i < text.size(); i++) {
            buttonWithArguments(text.get(i), callbackData.get(i));
        }
        return this;
    }

    public EditMessageBuilder button(List<String> text, List<String> callbackData, Integer numberRows) throws TelegramBotException {
        if (text.size() != callbackData.size()) {
            throw new TelegramBotException("Button collection size does not match callback collection");
        }
        for (int i = 0; i < text.size(); i++) {
            if (i % numberRows == 0) {
                addRowToKeyboard();
                inlineKeyboardRow = new ArrayList<>();
            }
            buttonWithArguments(text.get(i), callbackData.get(i));
        }
        return this;
    }

    public EditMessageBuilder buttonWithArguments(String text, String callbackData) {
        return button(text, callbackData + " " + text);
    }

    public EditMessageText build() {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.enableMarkdown(true);
        editMessageText.setText(sb.toString());
        editMessageText.setMessageId(messageId);


        addRowToKeyboard();

        if (!keyboard.isEmpty()) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        }

        return editMessageText;
    }

    public SendLocation buildLocation(Double latitude, Double longitude) {
        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(chatId);
        sendLocation.setReplyToMessageId(messageId);
        sendLocation.setLatitude(latitude);
        sendLocation.setLongitude(longitude);

        addRowToKeyboard();

        if (!keyboard.isEmpty()) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);
            sendLocation.setReplyMarkup(inlineKeyboardMarkup);
        }

        return sendLocation;
    }

    private void addRowToKeyboard() {
        if (inlineKeyboardRow != null) {
            keyboard.add(inlineKeyboardRow);
        }
    }
}
