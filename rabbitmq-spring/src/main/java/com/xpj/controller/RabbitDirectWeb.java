package com.xpj.controller;

import com.xpj.model.ParamRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitDirectWeb {

    private final RabbitTemplate rabbitTemplate;

    public RabbitDirectWeb(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping(value = "/direct")
    public String direct() {
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("-------------------return callback start------------------");
            System.out.println(message);
            System.out.println(routingKey);
            System.out.println("-------------------return callback end------------------");
        });
        rabbitTemplate.convertAndSend("test001.direct.exchange", "direct.save","123123");
        System.out.println("---------------------");
        return "0000";
    }

    @GetMapping(value = "/directObject")
    public String directObject() {


        ParamRequest request = new ParamRequest();
        request.setId("1");
        request.setName("zhang san");
        rabbitTemplate.convertAndSend("test001.direct2.exchange", "direct2.save",request);
        System.out.println("---------------------");
        return "0000";
    }
}
