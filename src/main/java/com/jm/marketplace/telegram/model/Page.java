package com.jm.marketplace.telegram.model;

import com.jm.marketplace.telegram.exception.TelegramBotException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

@Slf4j
public final class Page {
    /**
     * Cоздаем историю событий для пользователя.
     * Очередь - история для каждого message(состояние его переходов,
     * нажатий и прочего)
     * Внутреняя мапа - множество историй для определнного чата
     * Внешняя мама - множество множества историй.
     */
    private Deque<Update> history_message = new ArrayDeque<>();
    private HashMap<String, Deque<Update>> history_chat = new HashMap<>();
    @Getter
    private static HashMap<String, HashMap<String, Deque<Update>>> history = new HashMap<>();


    private Page() {
    }

    public static Page create() {
        history = new HashMap<>();
        return new Page();
    }

    public void addMessage(Update update) {
        {
            String chatId;
            String messageId;
            try {
                if (update.hasCallbackQuery()) {
                    chatId = update.getCallbackQuery()
                            .getMessage()
                            .getChatId()
                            .toString();
                    messageId = update.getCallbackQuery()
                            .getMessage()
                            .getMessageId()
                            .toString();
                } else if (update.hasMessage()) {
                    chatId = update.getMessage()
                            .getChatId()
                            .toString();
                    messageId = update.getMessage()
                            .getMessageId()
                            .toString();
                } else {
                    throw new IllegalArgumentException("");
                }
                    history_message = getHistory_message(chatId, messageId);
                    history_message.addLast(update);
                    history_chat = getHistory_chat(chatId);
                    history_chat.put(messageId, history_message);
                    history.put(chatId, history_chat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Page getPage() {
        return this;
    }

    public static HashMap<String, Deque<Update>> getHistory_chat(String chatId) {
        if(history.get(chatId) == null){
            return new HashMap<>();
        } else {
            return history.get(chatId);
        }
    }

    public static Deque<Update> getHistory_message(String chatId, String messageId) {
        if(history.get(chatId) == null || history.get(chatId).get(messageId) == null){
            return new ArrayDeque<>();
        } else {
            return history.get(chatId).get(messageId);
        }
    }

    public static Update getLastUpdate(String chatId, String messageId) {
        history.get(chatId).get(messageId).removeLast();
        return history.get(chatId).get(messageId).peekLast();
    }

}
