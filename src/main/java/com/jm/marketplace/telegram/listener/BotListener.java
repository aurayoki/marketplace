package com.jm.marketplace.telegram.listener;

import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Slf4j
public class BotListener implements StateMachineListener<States, Event> {
    @Override
    public void stateChanged(final State<States, Event> from, final State<States, Event> to) {
        log.info("Переход из состояния " + from.getId() + " в состояние " + to.getId());
    }

    @Override
    public void stateEntered(final State<States, Event> state) {

    }

    @Override
    public void stateExited(final State<States, Event> state) {

    }

    @Override
    public void eventNotAccepted(final Message<Event> message) {

    }

    @Override
    public void transition(final Transition<States, Event> transition) {

    }

    @Override
    public void transitionStarted(final Transition<States, Event> transition) {

    }

    @Override
    public void transitionEnded(final Transition<States, Event> transition) {

    }

    @Override
    public void stateMachineStarted(StateMachine stateMachine) {
        log.info("State machine started");
    }

    @Override
    public void stateMachineStopped(StateMachine stateMachine) {
        log.info("State amchine stopped");
    }

    @Override
    public void stateMachineError(StateMachine stateMachine, Exception exception) {
        log.info("State machine error \n" + exception.getStackTrace());
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {

    }

    @Override
    public void stateContext(StateContext stateContext) {

    }
}
