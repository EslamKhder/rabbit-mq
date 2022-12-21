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
public class DefaultDeadRouteExchangeConfig {

    @Value("${rabbit.default.queue.dead.route}")
    private String defaultQueue;

    @Value("${rabbit.direct.exchange}")
    private String fExchange;

    @Value("${rabbit.direct1.bi}")
    private String key;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Bean
    Queue createQueueDeadRoute(){
        Map<String,Object> atr = new HashMap<>();
        //atr.put("x-max-length",4);
        atr.put("x-message-ttl",4000);
        //atr.put("x-overflow","reject-publish");
        atr.put("x-dead-letter-exchange",fExchange);
        atr.put("x-dead-letter-routing-key",key);
        return new Queue(defaultQueue,true,false,false,atr);
    }

    @Bean
    public AmqpTemplate defaultQueueDeadRoute(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRoutingKey(defaultQueue);
        return rabbitTemplate;
    }

    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(createQueueDeadRoute());
    }
}
