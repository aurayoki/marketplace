package com.jm.marketplace.model.chat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String channelID;
    private String username;

    @Column(name = "recipient_user_id")
    private String recipient;
    private Date timeSent;
    private String content;

    public long getId() {
        return id;
    }

    public ChatMessage() {
        timeSent = new Date();
    }

    public ChatMessage(String username, String recipient, String content, Date timeSent, String channelID) {
        this.username = username;
        this.recipient = recipient;
        this.timeSent = timeSent;
        this.content = content;
        this.channelID = channelID;
    }

    public String getUsername() {
        return username;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
