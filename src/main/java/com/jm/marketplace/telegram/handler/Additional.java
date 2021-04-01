package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.builder.EditMessageBuilder;
import com.jm.marketplace.telegram.builder.MessageBuilder;
import com.jm.marketplace.telegram.exception.TelegramBotException;
import com.jm.marketplace.telegram.model.BotAdvertisement;
import com.jm.marketplace.telegram.model.Page;
import com.jm.marketplace.telegram.service.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;

@Slf4j
@BotCommand(message = "Добавить товар", command = "additional")
@Component
public class Additional implements Handler{
    private final BotService botService;
    private Page page = Page.create();;

    public Additional(BotService botService) {
        this.botService = botService;
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        try {
            String chatId;
            Integer messageId;

            if(update.hasCallbackQuery()) {
                chatId = update.getCallbackQuery().getMessage().getChatId().toString();
                messageId = update.getCallbackQuery().getMessage().getMessageId();
                EditMessageBuilder messageBuilder = EditMessageBuilder.create(chatId, messageId);
                BotAdvertisement botAdvertisement = BotAdvertisement.create();
                if(botAdvertisement.getEmptyTextAdvertisement(chatId).size()==0) {
                    messageBuilder.line("Товар сохранен");
                    botAdvertisement.save(chatId);
                    messageBuilder.row();
                    messageBuilder.button("Далее", "PAGE 1");
                } else {
                    List<String> textButton = botAdvertisement.getEmptyTextAdvertisement(chatId);
                    List<String> callback = botAdvertisement.getEmptyCallbackAdvertisement(chatId);
                    messageBuilder.row();
                    messageBuilder.button(textButton, callback, 2);
                    messageBuilder.row();
                    messageBuilder.line("Для добавления осталось ввести %d параметров", textButton.size());
                    botService.addAdvertisement(chatId);
                    page.addMessage(update);
                }
                return messageBuilder.build();
            } else if(update.hasMessage()) {
                chatId = update.getMessage().getChatId().toString();
                MessageBuilder messageBuilder = MessageBuilder.create(chatId);
                messageBuilder.clearKeyboard();
                BotAdvertisement botAdvertisement = BotAdvertisement.create();
                if(botAdvertisement.getEmptyTextAdvertisement(chatId).size()==0) {
                    botAdvertisement.save(chatId);
                    messageBuilder.line("Товар сохранен");
                    messageBuilder.row();
                    messageBuilder.button("Далее", "PAGE 1");
                } else {
                    List<String> textButton = botAdvertisement.getEmptyTextAdvertisement(chatId);
                    List<String> callback = botAdvertisement.getEmptyCallbackAdvertisement(chatId);
                    messageBuilder.row();
                    messageBuilder.button(textButton, callback, 2);
                    messageBuilder.row();
                    messageBuilder.line("Для добавления осталось ввести %d параметров", textButton.size());
                    botService.addAdvertisement(chatId);
                    page.addMessage(update);
                }
                return messageBuilder.build();
            }
            return new ErrorHandler().update(update);
        } catch (NullPointerException e) {
            log.error(e.getStackTrace().toString());
        } catch (TelegramBotException e) {
            log.error(e.getStackTrace().toString());
        }
        return new ErrorHandler().update(update);
    }

    public List<InlineKeyboardButton> emptyButton() {
        return null;
    }

    @Override
    public String toString() {
        return "Additional{}";
    }
}
