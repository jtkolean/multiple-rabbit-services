package com.jkolean.multirabbitservices;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatrixController {

    private final RabbitTemplate rabbitTemplateBlue;
    private final RabbitTemplate rabbitTemplateRed;

    public MatrixController(RabbitTemplate rabbitTemplateBlue, RabbitTemplate rabbitTemplateRed) {
        this.rabbitTemplateBlue = rabbitTemplateBlue;
        this.rabbitTemplateRed = rabbitTemplateRed;
    }

    @GetMapping(value = "/pill")
    public String pill(@RequestParam(value = "color") String color) {

        if ("blue".equalsIgnoreCase(color)) {
            rabbitTemplateBlue.convertAndSend("pill", color);
        } else {
            rabbitTemplateRed.convertAndSend("pill", color);
        }

        return "Remember: all I'm offering is the truth";
    }
}