package com.jm.marketplace.service.chat;

import com.jm.marketplace.model.chat.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    ChatMessage saveChatMessage(ChatMessage chatMessage);
    List<ChatMessage> findByChannelID(String channelID);
}
