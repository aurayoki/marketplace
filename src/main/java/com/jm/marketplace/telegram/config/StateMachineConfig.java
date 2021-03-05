package com.jm.marketplace.telegram.config;

import com.jm.marketplace.telegram.Bot;
import com.jm.marketplace.telegram.listener.BotListener;
import com.jm.marketplace.telegram.state.Event;
import com.jm.marketplace.telegram.state.States;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.config.common.annotation.AnnotationBuilder;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Event>{
    public StateMachineConfig() {
        super();
    }

    @Override
    public void configure(StateMachineConfigBuilder<States, Event> config) throws Exception {
        super.configure(config);
    }

    @Override
    public void configure(StateMachineModelConfigurer<States, Event> model) throws Exception {
        super.configure(model);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Event> config) throws Exception {
        config.withConfiguration().autoStartup(true).listener(new BotListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Event> states) throws Exception {
        states
                .withStates()
                .initial(States.START)
                .choice(States.ADDITION)
                .choice(States.ADD_CATEGORY)
                .choice(States.ADD_SUBCATEGORY)
                .choice(States.ADD_TYPE)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Event> transitions) throws Exception {
    }

    @Override
    public boolean isAssignable(AnnotationBuilder<org.springframework.statemachine.config.StateMachineConfig<States, Event>> builder) {
        return super.isAssignable(builder);
    }
}
