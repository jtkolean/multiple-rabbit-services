package com.jkolean.multirabbitservices;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class RabbitLocalConfig {

    private static final String queueName = "pill";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    CachingConnectionFactory cachingConnectionFactoryBlue() {
        return new CachingConnectionFactory(5672);
    }

// TODO: Uncomment if you wan to create the queue in this rabbitmq server
//    @Bean
//    SimpleMessageListenerContainer containerBlue(CachingConnectionFactory cachingConnectionFactoryBlue) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(cachingConnectionFactoryBlue);
//        container.setQueueNames(queueName);
//        container.setMessageListener();
//        return container;
//    }

    @Bean
    public RabbitTemplate rabbitTemplateBlue(CachingConnectionFactory cachingConnectionFactoryBlue) {
        return new RabbitTemplate(cachingConnectionFactoryBlue);
    }
}
