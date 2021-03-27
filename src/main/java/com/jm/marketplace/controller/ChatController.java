package com.jm.marketplace.controller;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.model.chat.ChatMessage;
import com.jm.marketplace.model.chat.channel.ChatChannel;
import com.jm.marketplace.service.chat.ChatMessageService;
import com.jm.marketplace.service.chat.chatchannel.ChatChannelService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    private final UserService userService;
    private final ChatChannelService chatChannelService;
    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatController(UserService userService, ChatChannelService chatChannelService, ChatMessageService chatMessageService) {
        this.userService = userService;
        this.chatChannelService = chatChannelService;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/personal.{channelId}")
    @SendTo("/topic/personal.{channelId}")
    public ChatMessage processMessage(@DestinationVariable String channelId, @Payload ChatMessage message) {
        if (!channelId.equals("undefined") && !message.getContent().equals("JOIN")) {
            chatMessageService.saveChatMessage(message);
        }
        return message;
    }

    @RequestMapping("profile/messenger/channel")
    public String chatPage(Model model, Principal user) {
        model.addAttribute("user", userService.findByEmail(user.getName()));
        return "/pages/chat";
    }

    @RequestMapping("profile/messenger")
    public String mainChatPage(Model model, Principal user) {
        UserDto currentUser = userService.findByEmail(user.getName());
        List<ChatChannel> channels = chatChannelService.findChatChannelsByUserID(currentUser.getId());
        for (ChatChannel channel: channels) {
            if (channel.getSender().getEmail().equals(currentUser.getEmail())) {
                channel.setSender(channel.getRecipient());
            }
        }
        // Нужна цена, название и имя владельца товара
        model.addAttribute("current_user", currentUser);
        model.addAttribute("channels", channels);
        return "/pages/messenger";
    }


}
