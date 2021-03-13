package com.jm.marketplace.telegram.action;

import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class ActionViewPage implements Action<States, Event> {
    @Override
    public void execute(StateContext<States, Event> context) {
        log.info("ActionChooseType context: " + context.toString());
    }
}
