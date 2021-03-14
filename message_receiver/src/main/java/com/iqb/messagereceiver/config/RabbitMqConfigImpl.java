package com.iqb.messagereceiver.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.iqb.messagereceiver.config.ConfigsUtils.*;

public class RabbitMqConfigImpl implements RabbitMqConfig {

    private final ConnectionFactory connectionFactory;
    public RabbitMqConfigImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Channel getChannel() throws IOException, TimeoutException {
        Channel channel = this.connectionFactory.newConnection()
                .createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        channel.exchangeDeclare(exchangeName, "direct");
        channel.queueBind(queueName, exchangeName, routingKey);
        return channel;
    }
}
