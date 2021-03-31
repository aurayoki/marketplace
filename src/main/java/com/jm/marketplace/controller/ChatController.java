package com.jm.marketplace.controller;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.model.chat.ChatMessage;
import com.jm.marketplace.model.chat.channel.ChatChannel;
import com.jm.marketplace.service.advertisement.AdvertisementService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    private final UserService userService;
    private final ChatChannelService chatChannelService;
    private final ChatMessageService chatMessageService;
    private final AdvertisementService advertisementService;

    @Autowired
    public ChatController(UserService userService, ChatChannelService chatChannelService,
                          ChatMessageService chatMessageService, AdvertisementService advertisementService) {
        this.userService = userService;
        this.chatChannelService = chatChannelService;
        this.chatMessageService = chatMessageService;
        this.advertisementService = advertisementService;
    }

    @MessageMapping("/personal.{channelId}")
    @SendTo("/topic/personal.{channelId}")
    public ChatMessage processMessage(@DestinationVariable String channelId, @Payload ChatMessage message) {
        if (!channelId.equals("undefined")) {
            chatMessageService.saveChatMessage(message);
        }
        return message;
    }

    @GetMapping("profile/messenger/channel/messages/{toUserID}-{advertisementID}")
    public String chatPage(Model model, Principal user, @PathVariable Long toUserID, @PathVariable Long advertisementID) {
        model.addAttribute("user", userService.findByEmail(user.getName()));
        model.addAttribute("recipient", userService.findById(toUserID));
        model.addAttribute("advertisement", advertisementService.findById(advertisementID));
        return "/pages/chat";
    }

    @RequestMapping("profile/messenger")
    public String mainChatPage(Model model, Principal user) {
        UserDto currentUser = userService.findByEmail(user.getName());
        List<ChatChannel> channels = chatChannelService.findChatChannelsByUserID(currentUser.getId());
        for (ChatChannel channel : channels) {
            if (channel.getSender().getEmail().equals(currentUser.getEmail())) {
                channel.setSender(channel.getRecipient());
            }
        }
        model.addAttribute("current_user", currentUser);
        model.addAttribute("channels", channels);
        return "/pages/messenger";
    }

}
