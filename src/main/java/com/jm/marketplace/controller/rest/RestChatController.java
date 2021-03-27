package com.jm.marketplace.controller.rest;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dao.ChatMessageDao;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.chat.ChatMessage;
import com.jm.marketplace.model.User;
import com.jm.marketplace.model.chat.channel.ChatChannel;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.chat.chatchannel.ChatChannelService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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

    @GetMapping("profile/messenger/get_channelID/{fromUserID}-{toUserID}-{advertisementID}")
    public String getChannelUuid(@PathVariable String fromUserID, @PathVariable String toUserID, @PathVariable String advertisementID) {
        ChatChannel chatChannel = chatChannelService.findChatChannelByUsersID(fromUserID, toUserID, advertisementID);
        if (chatChannel == null) {
            User userFrom = mapperFacade.map(userService.findById(Long.parseLong(fromUserID)), User.class);
            User userTo = mapperFacade.map(userService.findById(Long.parseLong(toUserID)), User.class);
            Advertisement advertisement = mapperFacade.map(advertisementService.findById(Long.parseLong(advertisementID)), Advertisement.class);
            chatChannel = new ChatChannel(userFrom, userTo, advertisement);

            chatChannelService.saveChatChannel(chatChannel);
        }
        return chatChannel.getChannelUuid();
    }

    @GetMapping("profile/messenger/channel/{chatID}")
    public List<ChatMessage> getChatMessages(@PathVariable String chatID) {
        List<ChatMessage> chatMessages =  chatMessageDao.findByChannelID(chatID);
        return chatMessages;
    }

//    private boolean findAdvertisement(Set<AdvertisementDto> advertisementDto, String advertisementID) {
//        for (AdvertisementDto advertisement: advertisementDto) {
//            if (advertisement.getId().toString().equals(advertisementID)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
