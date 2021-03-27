package com.jm.marketplace.dao;

import com.jm.marketplace.model.chat.channel.ChatChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatChannelDao extends JpaRepository<ChatChannel, Long> {
    @Query(" FROM"
            + "    ChatChannel channel"
            + "  WHERE"
            + "    channel.sender.id IN (:senderUserID, :recipientUserID) "
            + "  AND"
            + "    channel.recipient.id IN (:senderUserID, :recipientUserID)"
            + "  AND"
            + "    channel.advertisement.id = :advertisementID")
//            + "  AND "
//            + "    channel.advertisement.user.id = :recipientUserID")
     ChatChannel findExistingChannel(
            @Param("senderUserID") long senderUserID, @Param("recipientUserID") long recipientUserID, @Param("advertisementID") long advertisementID);

    @Query(" FROM"
            + "    ChatChannel channel"
            + "  WHERE"
            + "    channel.sender.id = :userID "
            + "  OR "
            + "    channel.recipient.id = :userID")
    List<ChatChannel> findChatChannelsByUserID(@Param("userID") long userID);

}
