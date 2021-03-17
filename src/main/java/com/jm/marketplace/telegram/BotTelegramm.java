package com.jm.marketplace.telegram;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@PropertySource(value = "classpath:telegram.properties", encoding = "UTF-8")
public class BotTelegramm  extends TelegramLongPollingBot {
    @Value("${bot.button.list_advertisement}")
    private String listAdvertisement;

    @Value("${bot.button.add_advertisement}")
    private String addAdvertisement;

    @Value("${bot.button.list_advertisement_pagination}")
    private String listAdvertisementPagination;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;
    private final UpdateReceiver updateReceiver;
    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final List<KeyboardRow> keyboard = new ArrayList<>();
    public BotTelegramm(UpdateReceiver updateReceiver) {
        this.updateReceiver = updateReceiver;
    }

    /**
     * After initialization actions:
     * - start task scheduler thread
     * - send start up report to bot admin
     */
    @PostConstruct
    public void init() {
        configKeyboard();
    }

    private void configKeyboard() {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }


    @Override
    public String getBotUsername() { return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        execute(updateReceiver.handle(update));
    }
}
