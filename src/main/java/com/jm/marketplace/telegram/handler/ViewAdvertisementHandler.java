package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class ViewAdvertisementHandler implements Handler {

    private final StateMachinePersister<States, Event, String> persister;
    private final StateMachineFactory<States, Event> stateMachineFactory;
    private final AdvertisementService advertisementService;

    @Autowired
    public ViewAdvertisementHandler(StateMachinePersister<States, Event, String> persister, StateMachineFactory<States, Event> stateMachineFactory, AdvertisementService advertisementService) {
        this.persister = persister;
        this.stateMachineFactory = stateMachineFactory;
        this.advertisementService = advertisementService;
    }

    @Override
    public List<EditMessageText> update(Update message) {
        return null;
    }
}
