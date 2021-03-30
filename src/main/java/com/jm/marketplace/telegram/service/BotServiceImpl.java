package com.jm.marketplace.telegram.service;

import com.jm.marketplace.telegram.model.Page;
import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BotServiceImpl implements BotService{
    private final StateMachinePersister<States, Event, String> persister;

    private final StateMachineFactory<States, Event> stateMachineFactory;
    private Page page;

    @Autowired
    public BotServiceImpl(StateMachinePersister<States, Event, String> persister, StateMachineFactory<States, Event> stateMachineFactory) {
        this.persister = persister;
        this.stateMachineFactory = stateMachineFactory;
    }


    @Override
    public void start(final String chatId) {
        final StateMachine<States, Event> stateMachine;
        stateMachine= stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("START", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.START);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void save(final String chatId) {

    }

    @Override
    public void showAdvertisement(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("VIEW_ADVERTISEMENT", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.VIEW_ADVERTISEMENT);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void showPageAdvertisement(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("VIEW_PAGE", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.VIEW_PAGE);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void addAdvertisement(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("ADDITIONAL", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.ADD);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void addCategory(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("ADD_CATEGORY", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.CHOOSE_CATEGORY);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void addSubcategory(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("ADD_SUBCATEGORY", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.CHOOSE_SUBCATEGORY);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void addType(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("ADD_TYPE", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.CHOOSE_TYPE);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void addPrice(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("ADD_PRICE", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.ADDITION_PRICE);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void addName(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("ADD_NAME", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.ADDITION_NAME);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void addDescription(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("ADD_DESCRIPTION", chatId);

        try {
            persister.restore(stateMachine, chatId);
            stateMachine.sendEvent(Event.ADDITION_DESCRIPTION);
            persister.persist(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public void back(final String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();

        try {
            persister.restore(stateMachine, chatId);
            log.debug("stateMachine after restore and send event: " + stateMachine.getState().toString());
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }

    @Override
    public String getState(String chatId) {
        final StateMachine<States, Event> stateMachine = stateMachineFactory.getStateMachine();
        try {
            persister.restore(stateMachine, chatId);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
        return stateMachine.getState().getId().toString();

    }

    @Override
    public void next(final String chatId) {

    }

}
