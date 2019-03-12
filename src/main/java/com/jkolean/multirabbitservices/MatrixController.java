package com.jkolean.multirabbitservices;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatrixController {

//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private ConnectionFactory blueRabbitConnectionFactory;
//    @Autowired
//    private ConnectionFactory redRabbitConnectionFactory;
//
//    @GetMapping(value = "/pill")
//    public String pill(@RequestParam(value = "color") String color) {
//        if("blue".equalsIgnoreCase(color)) {
//            // Bind this connection factory to current thread
//            SimpleResourceHolder.bind(blueRabbitConnectionFactory, "[blue]");
//            rabbitTemplate.convertAndSend("pill", color);
//            // Release connection factory from current thread
//            SimpleResourceHolder.unbind(blueRabbitConnectionFactory);
//        }
//        else {
//            SimpleResourceHolder.bind(redRabbitConnectionFactory, "[red]");
//            rabbitTemplate.convertAndSend("pill", color);
//            SimpleResourceHolder.unbind(redRabbitConnectionFactory);
//        }
//
//
//        return "Remember: all I'm offering is the truth";
//    }

    @Qualifier("blueRabbitTemplate")
    @Autowired
    private RabbitTemplate blueRabbitTemplate;

    @Autowired
    private RabbitTemplate redRabbitTemplate;

    @GetMapping(value="pill")
    public String pill(@RequestParam(value = "color") String color) {
        if("blue".equalsIgnoreCase(color)) {
            blueRabbitTemplate.convertAndSend("pill", color);
        }
        else {
            redRabbitTemplate.convertAndSend("pill", color);
        }
        return "Remember: all I'm offering is the truth";
    }

}