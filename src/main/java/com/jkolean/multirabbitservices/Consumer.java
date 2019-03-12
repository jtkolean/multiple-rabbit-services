package com.jkolean.multirabbitservices;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(containerFactory = "redRabbitListenerContainerFactory", queues = "pill")
    public void processRed(String msg) {
        // process incoming message
        System.out.println(msg + "Red!");
    }

    @RabbitListener(containerFactory = "blueRabbitListenerContainerFactory", queues = "pill")
    public void processBlue(String msg) {
        // process incoming message
        System.out.println(msg + "Blue!");
    }

}
