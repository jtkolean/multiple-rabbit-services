package com.jkolean.multirabbitservices;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(containerFactory = "blackRabbitListenerContainerFactory", queues = "pill")
    public void processBlack(String msg) {
        System.out.print((char)27 + "[30;47m Received message from BLACK container: " + msg);
        System.out.println((char)27 + "[39m"); // Reset the ANSI escape color to white
    }

    @RabbitListener(containerFactory = "blueRabbitListenerContainerFactory", queues = "pill")
    public void processBlue(String msg) {
        System.out.print((char)27 + "[94m Received message from BLUE container: " + msg);
        System.out.println((char)27 + "[39m"); // Reset the ANSI escape color to white
    }

    @RabbitListener(containerFactory = "redRabbitListenerContainerFactory", queues = "pill")
    public void processRed(String msg) {
        System.out.print((char)27 + "[31m Received message from RED container: " + msg);
        System.out.println((char)27 + "[39m"); // Reset the ANSI escape color to white
    }
}
