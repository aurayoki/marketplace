package com.jm.marketplace.controller.rest;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dao.ChatMessageDao;
import com.jm.marketplace.dto.chat.ChatMessageDto;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.User;
import com.jm.marketplace.model.chat.channel.ChatChannel;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.chat.chatchannel.ChatChannelService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestChatController {
    private ChatChannelService chatChannelService;
    private ChatMessageDao chatMessageDao;
    private MapperFacade mapperFacade;
    private UserService userService;
    private AdvertisementService advertisementService;

    public RestChatController(ChatChannelService chatChannelService, ChatMessageDao chatMessageDao, MapperFacade mapperFacade, UserService userService, AdvertisementService advertisementService) {
        this.chatChannelService = chatChannelService;
        this.chatMessageDao = chatMessageDao;
        this.mapperFacade = mapperFacade;
        this.userService = userService;
        this.advertisementService = advertisementService;
    }

    @GetMapping("profile/messenger/channel/channelID/{fromUserID}/{toUserID}/{advertisementID}")
    public String channelUuid(@PathVariable Long fromUserID,
                              @PathVariable Long toUserID,
                              @PathVariable Long advertisementID) {
        ChatChannel chatChannel = chatChannelService.findChatChannelByUsersID(fromUserID, toUserID, advertisementID);
        if (chatChannel == null) {
            User userFrom = mapperFacade.map(userService.findById(fromUserID), User.class);
            User userTo = mapperFacade.map(userService.findById(toUserID), User.class);
            Advertisement advertisement = mapperFacade.map(advertisementService.findById(advertisementID), Advertisement.class);
            chatChannel = new ChatChannel(userFrom, userTo, advertisement);

            chatChannelService.saveChatChannel(chatChannel);
        }
        return chatChannel.getChannelUuid();
    }

    @GetMapping("profile/messenger/channel/{chatID}")
    public List<ChatMessageDto> allMessagesByChatID(@PathVariable String chatID) {
        return mapperFacade.mapAsList(chatMessageDao.findByChannelID(chatID),
                ChatMessageDto.class);
    }
}
