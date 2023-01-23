package com.spring.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.spring.rabbitmq.model.Message;

@Component
public class ConsumerListener {

    @RabbitListener(queues = {"${rabbit.direct1.queue}","${rabbit.direct3.queue}"},
            containerFactory = "simpleRabbitListenerContainerFactory")
    public void receiveMessages(Message message){
        System.out.println(message);
    }
}
