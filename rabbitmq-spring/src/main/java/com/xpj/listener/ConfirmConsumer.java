package com.xpj.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.xpj.configuration.RabbitMQConfirmConfig.SIMPLE_CONFIRM_QUEUE;

@Component
public class ConfirmConsumer {

    @RabbitListener(queues = SIMPLE_CONFIRM_QUEUE)
    public void confirm(Channel channel, Message message){
        System.out.println("-------------------confirm received start------------------");
        System.out.println(new String(message.getBody()));
        System.out.println(message.getMessageProperties());
        System.out.println(channel);
        System.out.println("-------------------confirm received end------------------");
    }


}
