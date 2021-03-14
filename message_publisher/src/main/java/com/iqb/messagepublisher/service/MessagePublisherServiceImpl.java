package com.iqb.messagepublisher.service;

import com.rabbitmq.client.Channel;

import java.io.IOException;

import static com.iqb.messagepublisher.config.ConfigsUtils.exchangeName;
import static com.iqb.messagepublisher.config.ConfigsUtils.routingKey;
import static java.nio.charset.StandardCharsets.UTF_8;

public class MessagePublisherServiceImpl implements MessagePublisherService {

    private static final String messagePlaceholder = "Hello my name is, %s";

    private final Channel channel;

    public MessagePublisherServiceImpl(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void sendMessageToMq(String name) throws IOException {
        String message = String.format(messagePlaceholder, name.trim());
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes(UTF_8));
    }

}
