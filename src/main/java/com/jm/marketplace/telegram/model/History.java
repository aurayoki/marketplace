package com.jm.marketplace.telegram.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

@Slf4j
public final class History {
    /**
     * Cоздаем историю событий для пользователя.
     * Очередь - история для каждого message(состояние его переходов,
     * нажатий и прочего)
     * Внутреняя мапа - множество историй для определнного чата
     * Внешняя мама - множество множества историй.
     */
    private Deque<Update> historyMessage = new ArrayDeque<>();
    private HashMap<String, Deque<Update>> historyChat = new HashMap<>();
    @Getter
    private static HashMap<String, HashMap<String, Deque<Update>>> historyMap = new HashMap<>();


    private History() {
    }

    public static History create() {
        historyMap = new HashMap<>();
        return new History();
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
                    historyMessage = getHistoryMessage(chatId, messageId);
                    historyMessage.addLast(update);
                    historyChat = getHistoryChat(chatId);
                    historyChat.put(messageId, historyMessage);
                    historyMap.put(chatId, historyChat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public History getPage() {
        return this;
    }

    public static HashMap<String, Deque<Update>> getHistoryChat(String chatId) {
        if(historyMap.get(chatId) == null){
            return new HashMap<>();
        } else {
            return historyMap.get(chatId);
        }
    }

    public static Deque<Update> getHistoryMessage(String chatId, String messageId) {
        if(historyMap.get(chatId) == null || historyMap.get(chatId).get(messageId) == null){
            return new ArrayDeque<>();
        } else {
            return historyMap.get(chatId).get(messageId);
        }
    }

    public static Update getLastUpdate(String chatId, String messageId) {
        historyMap.get(chatId).get(messageId).removeLast();
        return historyMap.get(chatId).get(messageId).peekLast();
    }

}
