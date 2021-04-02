package com.jm.marketplace.telegram.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

@Slf4j
public final class History {
    private Deque<Update> historyMessage = new ArrayDeque<>();
    @Getter
    private static HashMap<String, Deque<Update>> chatHistory = new HashMap<>();


    private History() {
    }

    public static History create() {
        return new History();
    }

    public void addMessage(Update update) {
        {
            String chatId;
            try {
                if (update.hasCallbackQuery()) {
                    chatId = update.getCallbackQuery()
                            .getMessage()
                            .getChatId()
                            .toString();
                } else if (update.hasMessage()) {
                    chatId = update.getMessage()
                            .getChatId()
                            .toString();
                } else {
                    throw new IllegalArgumentException("");
                }
                Deque<Update> historyMessage = getHistoryChat(chatId);
                historyMessage.push(update);
                chatHistory.put(chatId, historyMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public History getPage() {
        return this;
    }

    public static Deque<Update> getHistoryChat(String chatId) {
        if(chatHistory.get(chatId) == null) {
            return new ArrayDeque<>();
        } else {
            return chatHistory.get(chatId);
        }
    }

    public static Update getLastUpdate(String chatId, String messageId) {
        chatHistory.get(chatId).removeFirst();
        return chatHistory.get(chatId).peekFirst();
    }

}
