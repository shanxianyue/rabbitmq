package com.xpj.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.xpj.configuration.RabbitMQReturnConfig.RETURN_QUEUE;

@Component
public class ReturnConsumer {

    @RabbitListener(queues = RETURN_QUEUE)
    public void confirm(Channel channel, Message message){
        System.out.println("-------------------return received start------------------");
        System.out.println(new String(message.getBody()));
        System.out.println(message.getMessageProperties());
        System.out.println(channel);
        System.out.println("-------------------return received end------------------");
    }


}
