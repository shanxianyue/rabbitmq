package com.xpj.controller;

import com.xpj.configuration.RabbitMQConfirmConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.xpj.configuration.RabbitMQReturnConfig.RETURN_EXCHANGE;

@RestController
public class RabbitReturnWeb {

    private final RabbitTemplate rabbitTemplate;

    public RabbitReturnWeb(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping(value = "/return")
    public String returnModel() {

        String msg = "return message!";
        rabbitTemplate.convertAndSend(RETURN_EXCHANGE,
                "return1.save",
                msg);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("-------------------return callback start------------------");
            System.out.println(message);
            System.out.println(routingKey);
            System.out.println("-------------------return callback end------------------");
        });

        return "0000";
    }
}
