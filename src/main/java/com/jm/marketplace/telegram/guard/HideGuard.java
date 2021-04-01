package com.jm.marketplace.telegram.guard;

import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class HideGuard implements Guard<States, Event> {

    @Override
    public boolean evaluate(StateContext<States, Event> context) {
        return false;
    }
}

