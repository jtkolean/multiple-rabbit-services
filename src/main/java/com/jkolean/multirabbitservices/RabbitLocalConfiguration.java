package com.jkolean.multirabbitservices;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableRabbit
@Profile("local")
public class RabbitLocalConfiguration {

    @Bean
    public ConnectionFactory blueRabbitConnectionFactory() {
        return new CachingConnectionFactory("localhost", 5673);
    }

    @Primary
    @Bean
    public ConnectionFactory redRabbitConnectionFactory() {
        return new CachingConnectionFactory("localhost", 5672);
    }

    @Qualifier(value = "blueRabbitTemplate")
    @Bean
    public RabbitTemplate blueRabbitTemplate() {
        return new RabbitTemplate(blueRabbitConnectionFactory());
    }

    @Bean
    @Primary
    public RabbitTemplate redRabbitTemplate() {
        return new RabbitTemplate(redRabbitConnectionFactory());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory redRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(redRabbitConnectionFactory());
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory blueRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(blueRabbitConnectionFactory());
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }
}
