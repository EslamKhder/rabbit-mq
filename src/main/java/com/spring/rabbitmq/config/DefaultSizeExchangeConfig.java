package com.spring.rabbitmq.config;


import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultSizeExchangeConfig {

    @Value("${rabbit.default.queue.size}")
    private String defaultQueue;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Bean
    Queue createQueueSize(){
        Map<String,Object> atr = new HashMap<>();
        atr.put("x-max-length",4);
        return new Queue(defaultQueue,true,false,false,atr);
    }

    @Bean
    public AmqpTemplate defaultQueueSize(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRoutingKey(defaultQueue);
        return rabbitTemplate;
    }

    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(createQueueSize());
    }
}
