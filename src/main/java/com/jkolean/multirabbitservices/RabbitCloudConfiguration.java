package com.jkolean.multirabbitservices;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRabbit
@Profile("cloud")
public class RabbitCloudConfiguration extends AbstractCloudConfig {

    @Bean
    public ConnectionFactory blueRabbitConnectionFactory() {
        return connectionFactory().rabbitConnectionFactory("blue-rabbit");
    }

    @Primary
    @Bean
    public ConnectionFactory redRabbitConnectionFactory() {
        return connectionFactory().rabbitConnectionFactory("red-rabbit");
    }

    @Qualifier(value="blueRabbitTemplate")
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
