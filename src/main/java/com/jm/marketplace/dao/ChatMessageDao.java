package com.jm.marketplace.dao;

import com.jm.marketplace.model.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageDao extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChannelID(String channelID);
}
