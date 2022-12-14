package com.spring.rabbitmq.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Value("${rabbit.direct1.queue}")
    private String directQueue1;

    @Value("${rabbit.direct2.queue}")
    private String directQueue2;

    @Value("${rabbit.direct3.queue}")
    private String directQueue3;

    @Value("${rabbit.direct1.bi}")
    private String binding1;

    @Value("${rabbit.direct2.bi}")
    private String binding2;

    @Value("${rabbit.direct3.bi}")
    private String binding3;

    @Value("${rabbit.direct.exchange}")
    private String exchange;

    @Bean
    Queue createDirectQueue1(){
        return new Queue(directQueue1,true,false,false);
    }

    @Bean
    Queue createDirectQueue2(){
        return new Queue(directQueue2,true,false,false);
    }

    @Bean
    Queue createDirectQueue3(){
        return new Queue(directQueue3,true,false,false);
    }

    @Bean
    DirectExchange createDirectExchange(){
        return new DirectExchange(exchange,true,false);
    }

    @Bean
    Binding createDirectBinding1(){
        return BindingBuilder.bind(createDirectQueue1()).to(createDirectExchange()).with(binding1);
    }

    @Bean
    Binding createDirectBinding2(){
        return BindingBuilder.bind(createDirectQueue2()).to(createDirectExchange()).with(binding2);
    }

    @Bean
    Binding createDirectBinding3(){
        return BindingBuilder.bind(createDirectQueue3()).to(createDirectExchange()).with(binding3);
    }

    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(createDirectQueue1());
        amqpAdmin.declareQueue(createDirectQueue2());
        amqpAdmin.declareQueue(createDirectQueue3());

        amqpAdmin.declareExchange(createDirectExchange());

        amqpAdmin.declareBinding(createDirectBinding1());
        amqpAdmin.declareBinding(createDirectBinding2());
        amqpAdmin.declareBinding(createDirectBinding3());
    }
}
