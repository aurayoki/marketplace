package com.jm.marketplace.service.chat;

import com.jm.marketplace.dao.ChatMessageDao;
import com.jm.marketplace.model.chat.ChatMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    private ChatMessageDao chatMessageDao;

    public ChatMessageServiceImpl(ChatMessageDao chatMessageDao) {
        this.chatMessageDao = chatMessageDao;
    }

    @Transactional
    @Override
    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        return chatMessageDao.save(chatMessage);
    }

    @Transactional
    @Override
    public List<ChatMessage> findByChannelID(String channelID) {
        return chatMessageDao.findByChannelID(channelID);
    }
}
