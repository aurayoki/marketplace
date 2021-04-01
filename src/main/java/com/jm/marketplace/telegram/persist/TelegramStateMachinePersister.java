package com.jm.marketplace.telegram.persist;

import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import java.util.HashMap;

public class TelegramStateMachinePersister implements StateMachinePersist<States, Event, String> {
    private final HashMap<String, StateMachineContext<States, Event>> contexts = new HashMap<>();

    @Override
    public void write(final StateMachineContext<States, Event> context, String contextObj) throws Exception {
        contexts.put(contextObj, context);
    }

    @Override
    public StateMachineContext<States, Event> read(String contextObj) throws Exception {
        return contexts.get(contextObj);
    }
}
