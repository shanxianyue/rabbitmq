package com.xpj.controller;

import com.xpj.model.ParamRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitTopicWeb {


    private final RabbitTemplate rabbitTemplate;

    public RabbitTopicWeb(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @GetMapping(value = "/topic")
    public String topic() {
        rabbitTemplate.convertAndSend("test001.topic.exchange",
                "topic.save",
                "this is a topic message, routingKey: topic.save");
        rabbitTemplate.convertAndSend("test001.topic.exchange",
                "topic.a",
                "this is a topic message, routingKey: topic.a");
        System.out.println("---------------------");
        return "0000";
    }

    @GetMapping(value = "/topicObject")
    public String topicObject() {
        ParamRequest request = new ParamRequest();
        request.setId("1");
        request.setName("topic zhang san");
        rabbitTemplate.convertAndSend("test001.topic2.exchange", "topic2.save",request);
        System.out.println("---------------------");
        return "0000";
    }
}
