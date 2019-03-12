package com.jkolean.multirabbitservices;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatrixController {

    @Qualifier("blueRabbitTemplate")
    @Autowired
    private RabbitTemplate blueRabbitTemplate;

    @Autowired
    private RabbitTemplate redRabbitTemplate;

    @GetMapping(value = "pill")
    public String pill(@RequestParam(value = "color") String color) {
        if ("blue".equalsIgnoreCase(color)) {
            blueRabbitTemplate.convertAndSend("pill", color);
        } else {
            redRabbitTemplate.convertAndSend("pill", color);
        }
        return "Remember: all I'm offering is the truth";
    }

}