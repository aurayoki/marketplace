package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import com.jm.marketplace.telegram.util.AdvertisementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@BotCommand(command = "PAGE", message = "")
public class ViewPageHandler implements Handler {

    private final StateMachinePersister<States, Event, String> persister;
    private final StateMachineFactory<States, Event> stateMachineFactory;
    private final AdvertisementService advertisementService;
    private final AdvertisementUtils advertisementUtils;

    @Autowired
    public ViewPageHandler(StateMachinePersister<States, Event, String> persister, StateMachineFactory<States, Event> stateMachineFactory, AdvertisementService advertisementService, AdvertisementUtils advertisementUtils) {
        this.persister = persister;
        this.stateMachineFactory = stateMachineFactory;
        this.advertisementService = advertisementService;
        this.advertisementUtils = advertisementUtils;
    }


    @Override
    public EditMessageText update(Update update) {
        Integer currentPage = Integer.parseInt(update.getCallbackQuery().getData().substring(5));

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setText(advertisementUtils.getAdvertisementTextForCurrentPage(currentPage));
        editMessageText.setReplyMarkup(getInlineButtonsPagination(currentPage));
        editMessageText.setChatId(update.getMessage().getChatId().toString());
        return editMessageText;
    }

    private InlineKeyboardMarkup getInlineButtonsPagination(int currentPage) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getInlineKeyboardButtonsAdvertisementForCurrentPage(currentPage));
        rowList.add(advertisementUtils.getInlineKeyboardButtonPagination());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }


    private List<InlineKeyboardButton> getInlineKeyboardButtonsAdvertisementForCurrentPage(Integer currentPage) {
        List<AdvertisementDto> advertisementDtos = advertisementUtils.getAdvertisementForCurrentPage(currentPage);
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        for(AdvertisementDto advertisementDto : advertisementDtos) {
            InlineKeyboardButton advertisementButton = new InlineKeyboardButton();
            advertisementButton.setText(advertisementDto.getName());
            advertisementButton.setCallbackData("GOODS " + advertisementDto.getId());
            keyboardButtonsRow.add(advertisementButton);
        }

        return keyboardButtonsRow;
    }


}
