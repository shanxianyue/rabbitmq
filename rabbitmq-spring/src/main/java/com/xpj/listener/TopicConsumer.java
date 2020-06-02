package com.xpj.listener;

import com.rabbitmq.client.Channel;
import com.xpj.model.ParamRequest;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @RabbitListener(queues = "test001.topic.queue")
    public void direct(Channel channel, Message message){
        System.out.println(new String(message.getBody()));
        System.out.println(message.getMessageProperties());
        System.out.println(channel);
    }

    @RabbitListener(queues = "test001.topic2.queue")
    public void direct2(Channel channel, ParamRequest paramRequest){
        System.out.println(paramRequest);
        System.out.println(channel);
    }
}
