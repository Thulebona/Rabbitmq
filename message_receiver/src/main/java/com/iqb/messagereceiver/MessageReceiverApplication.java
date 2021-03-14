package com.iqb.messagereceiver;


import com.iqb.messagereceiver.config.ConfigsUtils;
import com.iqb.messagereceiver.config.RabbitMqConfig;
import com.iqb.messagereceiver.config.RabbitMqConfigImpl;
import com.iqb.messagereceiver.listener.MessageReceiverListener;
import com.iqb.messagereceiver.listener.MessageReceiverListenerImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageReceiverApplication {
    private final MessageReceiverListener receiverListener;

    public MessageReceiverApplication(MessageReceiverListener receiverListener) {
        this.receiverListener = receiverListener;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMqConfig rabbitMqConfig = new RabbitMqConfigImpl(ConfigsUtils.getConnectionFactory());
        MessageReceiverListener receiverService = new MessageReceiverListenerImpl(rabbitMqConfig.getChannel());
        MessageReceiverApplication app = new MessageReceiverApplication(receiverService);
        app.receiverListener.readingMessageFromMqAsync();
    }


}
