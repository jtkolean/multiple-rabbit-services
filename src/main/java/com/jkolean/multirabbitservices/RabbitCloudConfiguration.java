package com.jkolean.multirabbitservices;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableRabbit
@Profile("cloud")
public class RabbitCloudConfiguration extends AbstractCloudConfig {

    @Bean
    public ConnectionFactory blackRabbitConnectionFactory() {
        return connectionFactory().rabbitConnectionFactory("black-rabbit");
    }

    @Bean
    public ConnectionFactory blueRabbitConnectionFactory() {
        return connectionFactory().rabbitConnectionFactory("blue-rabbit");
    }

    @Primary
    @Bean
    public ConnectionFactory redRabbitConnectionFactory() {
        return connectionFactory().rabbitConnectionFactory("red-rabbit");
    }


    @Bean
    public AmqpAdmin blackRabbitAdmin() {
        AmqpAdmin admin1 = new RabbitAdmin(blackRabbitConnectionFactory());
        admin1.declareQueue(new Queue("pill", false, false, false));
        return admin1;
    }

    @Bean
    public AmqpAdmin blueRabbitAdmin() {
        AmqpAdmin admin1 = new RabbitAdmin(blueRabbitConnectionFactory());
        admin1.declareQueue(new Queue("pill", false, false, false));
        return admin1;
    }

    @Primary
    @Bean
    public AmqpAdmin redRabbitAdmin() {
        AmqpAdmin admin1 = new RabbitAdmin(redRabbitConnectionFactory());
        admin1.declareQueue(new Queue("pill", false, false, false));
        return admin1;
    }

    @Qualifier(value = "blackRabbitTemplate")
    @Bean
    public RabbitTemplate blackRabbitTemplate() {
        return new RabbitTemplate(blackRabbitConnectionFactory());
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
    public SimpleRabbitListenerContainerFactory blackRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(blackRabbitConnectionFactory());
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

    @Bean
    public SimpleRabbitListenerContainerFactory redRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(redRabbitConnectionFactory());
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }
}
