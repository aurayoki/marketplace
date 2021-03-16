package com.jm.marketplace.telegram.handler;

import com.jm.marketplace.telegram.annotations.BotCommand;
import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;

@Service
@BotCommand(message = "", command = "CHOOSE_TYPE")
public class AdditionCategoryHandler implements Handler {

    private final StateMachinePersister<States, Event, String> persister;
    private final StateMachineFactory<States, Event> stateMachineFactory;

    public AdditionCategoryHandler(StateMachinePersister<States, Event, String> persister, StateMachineFactory<States, Event> stateMachineFactory) {
        this.persister = persister;
        this.stateMachineFactory = stateMachineFactory;
    }

    @Override
    public List<EditMessageText> update(String message) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.
        return null;
    }
}
