package com.jm.marketplace.telegram.config;

import com.jm.marketplace.telegram.action.*;
import com.jm.marketplace.telegram.listener.BotListener;
import com.jm.marketplace.telegram.persist.TelegramStateMachinePersister;
import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.config.common.annotation.AnnotationBuilder;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Event> {
    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Event> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new BotListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Event> states) throws Exception {
        states
                .withStates()
                .initial(States.START)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Event> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.START)
                .target(States.ADDITION)
                .event(Event.ADD)

                .and()
                .withExternal()
                .source(States.ADDITION)
                .target(States.ADD_CATEGORY)
                .event(Event.CHOOSE_CATEGORY)

                .and()
                .withExternal()
                .source(States.ADD_CATEGORY)
                .target(States.ADDITION)
                .event(Event.ADD)

                .and()
                .withExternal()
                .source(States.START)
                .target(States.VIEW_ALL_PAGES)
                .event(Event.VIEW_PAGE)

                .and()
                .withExternal()
                .source(States.ADDITION)
                .target(States.ADD_SUBCATEGORY)
                .event(Event.CHOOSE_SUBCATEGORY)

                .and()
                .withExternal()
                .source(States.ADD_SUBCATEGORY)
                .target(States.ADDITION)
                .event(Event.ADD)

                .and()
                .withExternal()
                .source(States.ADDITION)
                .target(States.ADD_TYPE)
                .event(Event.CHOOSE_TYPE)

                .and()
                .withExternal()
                .source(States.ADD_TYPE)
                .target(States.ADDITION)
                .event(Event.ADD)

                .and()
                .withExternal()
                .source(States.ADDITION)
                .target(States.ADD_DESCRIPTION)
                .event(Event.ADDITION_DESCRIPTION)

                .and()
                .withExternal()
                .source(States.ADD_DESCRIPTION)
                .target(States.ADDITION)
                .event(Event.ADD)

                .and()
                .withExternal()
                .source(States.ADDITION)
                .target(States.ADD_PRICE)
                .event(Event.ADDITION_PRICE)

                .and()
                .withExternal()
                .source(States.ADD_PRICE)
                .target(States.ADDITION)
                .event(Event.ADD)

                .and()
                .withExternal()
                .source(States.ADDITION)
                .target(States.ADD_NAME)
                .event(Event.ADDITION_NAME)

                .and()
                .withExternal()
                .source(States.ADD_NAME)
                .target(States.ADDITION)
                .event(Event.ADD)

                .and()
                .withExternal()
                .source(States.ADDITION)
                .target(States.VIEW_ALL_PAGES)
                .event(Event.VIEW_PAGE)

                .and()
                .withExternal()
                .source(States.VIEW_ALL_PAGES)
                .target(States.VIEW_ADVERTISEMENT)
                .event(Event.VIEW_ADVERTISEMENT)

                .and()
                .withExternal()
                .source(States.VIEW_ADVERTISEMENT)
                .target(States.VIEW_ALL_PAGES)
                .event(Event.VIEW_PAGE);
    }

    @Bean
    public Action<States, Event> actionAddition() {
        return new ActionAddition();
    }

    @Bean
    public Action<States, Event> actionChooseCategory() {
        return new ActionChooseCategory();
    }

    @Bean
    public Action<States, Event> actionChooseSubcategory() {
        return new ActionChooseSubcategory();
    }

    @Bean
    public Action<States, Event> actionChooseType() {
        return new ActionChooseType();
    }

    @Bean
    public Action<States, Event> actionError() {
        return new ActionError();
    }

    @Bean
    public Action<States, Event> actionSave() {
        return new ActionSave();
    }

    @Bean
    public Action<States, Event> actionViewAdvertisement() {
        return new ActionViewAdv();
    }

    @Bean
    public Action<States, Event> actionViewPage() {
        return new ActionViewPage();
    }

    @Bean
    public Action<States, Event> actionBack() {return new ActionBack();}

    @Bean
    public StateMachinePersister<States, Event, String> persister() {
        return new DefaultStateMachinePersister<States, Event, String>(new TelegramStateMachinePersister());
    }
}
