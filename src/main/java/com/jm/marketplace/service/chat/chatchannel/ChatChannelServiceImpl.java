package com.jm.marketplace.service.chat.chatchannel;

import com.jm.marketplace.dao.ChatChannelDao;
import com.jm.marketplace.model.chat.channel.ChatChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChatChannelServiceImpl implements ChatChannelService {
    private ChatChannelDao chatChannelDao;

    @Autowired
    public ChatChannelServiceImpl(ChatChannelDao chatChannelDao) {
        this.chatChannelDao = chatChannelDao;
    }

    @Override
    public ChatChannel findChatChannelByUsersID(Long fromUserID, Long toUserID, Long advertisementID) {
        return chatChannelDao.findExistingChannel(fromUserID, toUserID, advertisementID);
    }

    public void saveChatChannel(ChatChannel chatChannel) {
        chatChannelDao.save(chatChannel);
    }

    public List<ChatChannel> findChatChannelsByUserID(long userID) {
        return chatChannelDao.findChatChannelsByUserID(userID);
    }

}
