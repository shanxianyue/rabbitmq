package com.xpj.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfirmConfig {


    public static final String SIMPLE_CONFIRM_QUEUE = "test001.simple.confirm.queue";
    public static final String SIMPLE_CONFIRM_EXCHANGE = "test001.simple.confirm.exchange";

    public static final String CORRELATED_CONFIRM_QUEUE = "test001.correlated.confirm.queue";
    public static final String CORRELATED_CONFIRM_EXCHANGE = "test001.correlated.confirm.exchange";

    @Bean
    public Queue queueSimpleConfirm(){
        return new Queue(SIMPLE_CONFIRM_QUEUE, true);
    }

    @Bean
    public Exchange exchangeSimpleConfirm(){
        return new TopicExchange(SIMPLE_CONFIRM_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingSimpleConfirm(){
        return BindingBuilder.bind(queueSimpleConfirm())
                .to(exchangeSimpleConfirm())
                .with("simple.confirm.#")
                .noargs();
    }

    @Bean
    public Queue queueCorrelatedConfirm(){
        return new Queue(CORRELATED_CONFIRM_QUEUE, true);
    }

    @Bean
    public Exchange exchangeCorrelatedConfirm(){
        return new TopicExchange(CORRELATED_CONFIRM_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingCorrelatedConfirm(){
        return BindingBuilder.bind(queueCorrelatedConfirm())
                .to(exchangeCorrelatedConfirm())
                .with("correlated.confirm.#")
                .noargs();
    }

}
