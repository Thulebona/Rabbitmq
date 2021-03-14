package com.iqb.messagepublisher.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface RabbitMqConfig {
    Channel getChannel() throws IOException, TimeoutException;
}
