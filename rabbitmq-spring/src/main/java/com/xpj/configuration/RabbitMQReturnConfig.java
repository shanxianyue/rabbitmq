package com.xpj.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQReturnConfig {


    public static final String RETURN_QUEUE = "test001.return.queue";
    public static final String RETURN_EXCHANGE = "test001.return.exchange";


    @Bean
    public Queue queueReturnConfirm(){
        return new Queue(RETURN_QUEUE, true);
    }

    @Bean
    public Exchange exchangeReturnConfirm(){
        return new TopicExchange(RETURN_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingConfirm(){
        return BindingBuilder.bind(queueReturnConfirm())
                .to(exchangeReturnConfirm())
                .with("return.#")
                .noargs();
    }


}
