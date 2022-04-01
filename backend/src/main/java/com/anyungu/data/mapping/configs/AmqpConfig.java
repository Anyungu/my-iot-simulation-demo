package com.anyungu.data.mapping.configs;

import com.anyungu.data.mapping.v1.controllers.AmqpSender;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
    @Bean
    public TopicExchange topic() {
        return new TopicExchange("ksensei.main");
    }

    @Bean
    public Queue notificationsQueue() {
        return new Queue("main-queue", true);
    }

    @Bean
    public Binding bindNotifications(TopicExchange topic, Queue notificationsQueue) {
        return BindingBuilder.bind(notificationsQueue).to(topic).with("topic.sample" + ".*");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter() {
            @Override
            public Object fromMessage(Message message, Object conversionHint) throws MessageConversionException {
                message.getMessageProperties().setContentType("application/json");
                return super.fromMessage(message, conversionHint);
            }
        };
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
                                                           SimpleRabbitListenerContainerFactoryConfigurer configurer) {

        try {SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
            configurer.configure(factory, connectionFactory);
            factory.setMessageConverter(jsonMessageConverter());
            return factory;}catch (Exception e) {
            System.out.println(e.getMessage());
            return  null;
        }

    }

    @Bean
    public AmqpSender sender() {
        return new AmqpSender();
    }
}