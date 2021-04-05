package com.jm.marketplace.model.chat.channel;

import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.User;
import com.jm.marketplace.util.chat.GenerateChatChannelID;

import javax.persistence.*;

@Entity
@Table(name = "chat_channel")
public class ChatChannel {
    @Id
    private String channelUuid;

    @OneToOne
    @JoinColumn(name = "userID_first")
    private User sender;


    @OneToOne
    @JoinColumn(name = "userID_second")
    private User recipient;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    public ChatChannel() {
    }

    public ChatChannel(User sender, User recipient, Advertisement advertisement) {
        this.channelUuid = GenerateChatChannelID.getRandomChannelID();
        this.sender = sender;
        this.recipient = recipient;
        this.advertisement = advertisement;
    }

    public String getChannelUuid() {
        return channelUuid;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
