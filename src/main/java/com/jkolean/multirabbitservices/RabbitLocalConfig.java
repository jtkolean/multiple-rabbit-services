package com.jkolean.multirabbitservices;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitLocalConfig extends AbstractCloudConfig {

//    @Bean
//    public ConnectionFactory blueRabbitConnectionFactory() {
//        return connectionFactory().rabbitConnectionFactory("blue-rabbit");
//    }
//
//    @Bean
//    public ConnectionFactory redRabbitConnectionFactory() {
//        return connectionFactory().rabbitConnectionFactory("red-rabbit");
//    }
//
//@Bean
//@Primary
//public ConnectionFactory routing() {
//    SimpleRoutingConnectionFactory rcf = new SimpleRoutingConnectionFactory();
//    Map<Object, ConnectionFactory> map = new HashMap<>();
//    map.put("[red]", redRabbitConnectionFactory());
//    map.put("[blue]", blueRabbitConnectionFactory());
//    rcf.setTargetConnectionFactories(map);
//    return rcf;
//}
//


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

//    @Bean
//    public ConnectionFactory blueRabbitConnectionFactory() {
//        return connectionFactory().rabbitConnectionFactory("blue-rabbit");
//    }
//
//    @Bean
//    public ConnectionFactory redRabbitConnectionFactory() {
//        return connectionFactory().rabbitConnectionFactory("red-rabbit");
//    }
//
//    @Bean
//    @Primary
//    public ConnectionFactory routing() {
//        SimpleRoutingConnectionFactory rcf = new SimpleRoutingConnectionFactory();
//        Map<Object, ConnectionFactory> map = new HashMap<>();
//        map.put("[red]", redRabbitConnectionFactory());
//        map.put("[blue]", blueRabbitConnectionFactory());
//        rcf.setTargetConnectionFactories(map);
//        return rcf;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate(routing());
//    }
}