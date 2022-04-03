// package com.anyungu.data.mapping.v1.controllers;


// import org.springframework.amqp.core.TopicExchange;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.factory.annotation.Autowired;

// public class AmqpSender {

//     @Autowired
//     private RabbitTemplate template;

//     @Autowired
//     private TopicExchange topic;

//     /**
//      *
//      * @param channelBinding
//      * @param data
//      */
//     public void send(String channelBinding, Object data) {
//         template.convertAndSend(topic.getName(), channelBinding, data);
//     }
// }