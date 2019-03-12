package com.jkolean.multirabbitservices;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRabbit
@Profile("local")
public class RabbitLocalConfiguration {

    @Bean
    public ConnectionFactory blueRabbitConnectionFactory() {
        return new CachingConnectionFactory("localhost", 5673);
    }

    @Bean
    public ConnectionFactory redRabbitConnectionFactory() {
        return new CachingConnectionFactory("localhost", 5672);
    }

    @Primary
    @Bean
    public ConnectionFactory connectionFactoryRouter() {
        SimpleRoutingConnectionFactory rcf = new SimpleRoutingConnectionFactory();
        Map<Object, ConnectionFactory> map = new HashMap<>();
        map.put("[red]", redRabbitConnectionFactory());
        map.put("[blue]", blueRabbitConnectionFactory());
        rcf.setTargetConnectionFactories(map);
        return rcf;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactoryRouter) {
        return new RabbitTemplate(connectionFactoryRouter);
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
