package com.jm.marketplace.service.chat.chatchannel;

import com.jm.marketplace.model.chat.channel.ChatChannel;

import java.util.List;

public interface ChatChannelService {
    ChatChannel findChatChannelByUsersID(String fromUserID, String toUserID, String advertisementID);
    void saveChatChannel(ChatChannel chatChannel);
    List<ChatChannel> findChatChannelsByUserID(long senderID);
}
