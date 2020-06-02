package com.xpj.controller;

import com.rabbitmq.client.Return;
import com.xpj.configuration.RabbitMQConfirmConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RabbitConfirmWeb {

    private final RabbitTemplate rabbitTemplate;

    public RabbitConfirmWeb(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 需要设置 暂未调通
     * CachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
     */
    @GetMapping(value = "/simple/confirm")
    public String simpleConfirm() {
        String msg = "simple confirm message!";
        rabbitTemplate.convertAndSend(RabbitMQConfirmConfig.SIMPLE_CONFIRM_EXCHANGE,
                "simple.confirm.save",
                msg);

//        boolean isConfirm = rabbitTemplate.waitForConfirms(10000);
//        System.out.println("is confirm " + isConfirm);
        return "0000";
    }


    /**
     * 需要设置CachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
     */
    @GetMapping(value = "/correlated/confirm")
    public String correlatedConfirm() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("-------------------confirm callback start------------------");
            System.out.println(ack);
            System.out.println(correlationData);
            System.out.println(cause);
            System.out.println("-------------------confirm callback end------------------");
        });

        String msg = "correlated confirm message!";
        rabbitTemplate.convertAndSend(RabbitMQConfirmConfig.SIMPLE_CONFIRM_EXCHANGE,
                "correlated.confirm.save",
                msg, new CorrelationData(UUID.randomUUID().toString()));
        return "0000";
    }
}
