package com.xpj.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDirectConfig {

    @Bean
    public Queue queueDirect001(){
        return new Queue("test001.direct.queue", true);
    }

    @Bean
    public Exchange exchangeDirect001(){
        return new DirectExchange("test001.direct.exchange", true, false);
    }

    @Bean
    public Binding bindingDirect001(){
//        return new Binding("test001.direct", Binding.DestinationType.QUEUE, "test001.direct.exchange", "direct.save", null);
        return BindingBuilder.bind(queueDirect001()).to(exchangeDirect001()).with("direct.save").noargs();
    }


    @Bean
    public Queue queueDirect002(){
        return new Queue("test001.direct2.queue", true);
    }

    @Bean
    public Exchange exchangeDirect002(){
        return new DirectExchange("test001.direct2.exchange", true, false);
    }

    @Bean
    public Binding bindingDirect002(){
//        return new Binding("test001.direct", Binding.DestinationType.QUEUE, "test001.direct.exchange", "direct.save", null);
        return BindingBuilder.bind(queueDirect002())
                .to(exchangeDirect002())
                .with("direct2.save")
                .noargs();
    }



}
