package com.anyungu.data.mapping.v1.controllers;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketSender {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     *
     * @param topic
     * @param data
     */
    public void send(String topic, Object data) {
        template.convertAndSend(topic, data);
    }
}
