package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.model.History;
import com.jm.marketplace.telegram.util.AdvertisementUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

@Component
@Slf4j
@BotCommand(command = "ADV", message = "")
public class MapHandler implements Handler{
    private final AdvertisementUtils advertisementUtils;
    private History history = History.create();;

    public MapHandler(AdvertisementUtils advertisementUtils) {
        this.advertisementUtils = advertisementUtils;
    }

    @Override
    public BotApiMethod<? extends Serializable> update(Update update) {
        return null;
    }
}
