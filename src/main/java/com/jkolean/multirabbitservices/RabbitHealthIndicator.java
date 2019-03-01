package com.jkolean.multirabbitservices;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleResourceHolder;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

@Component
public class RabbitHealthIndicator extends AbstractHealthIndicator {

    private final RabbitTemplate rabbitTemplate;
    private ConnectionFactory blueRabbitConnectionFactory;
    private ConnectionFactory redRabbitConnectionFactory;

    public RabbitHealthIndicator(RabbitTemplate rabbitTemplate,
                                 ConnectionFactory blueRabbitConnectionFactory,
                                 ConnectionFactory redRabbitConnectionFactory) {
        Assert.notNull(rabbitTemplate, "RabbitTemplate must not be null.");
        this.rabbitTemplate = rabbitTemplate;
        this.blueRabbitConnectionFactory = blueRabbitConnectionFactory;
        this.redRabbitConnectionFactory = redRabbitConnectionFactory;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        builder.up().withDetail("version", getVersion(blueRabbitConnectionFactory, "blue"));
        builder.up().withDetail("version", getVersion(redRabbitConnectionFactory, "red"));
    }

    private String getVersion(ConnectionFactory connectionFactory, String routingKey) {
        SimpleResourceHolder.bind(connectionFactory, "[" + routingKey + "]");

        String version = this.rabbitTemplate.execute(new ChannelCallback<String>() {
            @Override
            public String doInRabbit(Channel channel) {
                Map<String, Object> serverProperties = channel.getConnection()
                        .getServerProperties();
                return serverProperties.get("version").toString();
            }
        });
        SimpleResourceHolder.unbind(connectionFactory);
        return version;
    }
}
