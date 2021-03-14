package com.iqb.messagereceiver.listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static com.iqb.messagereceiver.config.ConfigsUtils.queueName;
import static java.nio.charset.StandardCharsets.UTF_8;

public class MessageReceiverListenerImpl implements MessageReceiverListener {

    private static final String messagePlaceholder = "Hello %s, I am your father";

    private final Channel channel;

    public MessageReceiverListenerImpl(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void readingMessageFromMqAsync() {
        Thread thread = new Thread(() -> {
            try {
                DeliverCallback deliverCallback = (consumerTag, delivery) -> printMessage(delivery.getBody());
                channel.basicConsume(queueName, true, deliverCallback, cancelCallback -> {
                });
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
        thread.start();
    }

    @Override
    public void printMessage(byte[] data) {
        String message = new String(data, UTF_8);
        if (StringUtils.isNotBlank(message) && StringUtils.split(message, ",").length > 1) {
            message = String.format(messagePlaceholder, StringUtils.split(message, ",")[1].trim());
            System.out.println(message);
        }
    }
}
