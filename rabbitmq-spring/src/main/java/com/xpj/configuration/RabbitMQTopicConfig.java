package com.xpj.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicConfig {



    @Bean
    public Queue queueTopic001(){
        return new Queue("test001.topic.queue", true);
    }

    @Bean
    public Exchange exchangeTopic001(){
        return new TopicExchange("test001.topic.exchange", true, false);
    }

    @Bean
    public Binding bindingTopic001(){
        return BindingBuilder.bind(queueTopic001())
                .to(exchangeTopic001())
                .with("topic.#")
                .noargs();
    }


    @Bean
    public Queue queueTopic002(){
        return new Queue("test001.topic2.queue", true);
    }

    @Bean
    public Exchange exchangeTopic002(){
        return new TopicExchange("test001.topic2.exchange", true, false);
    }

    @Bean
    public Binding bindingTopic002(){
        return BindingBuilder.bind(queueTopic002())
                .to(exchangeTopic002())
                .with("topic2.#")
                .noargs();
    }



}
