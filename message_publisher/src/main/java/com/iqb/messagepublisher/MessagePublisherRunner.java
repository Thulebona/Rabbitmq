package com.iqb.messagepublisher;


import com.iqb.messagepublisher.config.ConfigsUtils;
import com.iqb.messagepublisher.config.CustomScanner;
import com.iqb.messagepublisher.config.RabbitMqConfig;
import com.iqb.messagepublisher.config.RabbitMqConfigImpl;
import com.iqb.messagepublisher.service.MessagePublisherService;
import com.iqb.messagepublisher.service.MessagePublisherServiceImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class MessagePublisherRunner {

    private final MessagePublisherService publisherService;
    private final CustomScanner customScanner;
    public MessagePublisherRunner(MessagePublisherService publisherService, CustomScanner customScanner) {
        this.publisherService = publisherService;
        this.customScanner = customScanner;
    }

    public void promoteUserInput( ) throws IOException, TimeoutException {
        boolean isExist = false;
        while (!isExist) {
            String name = customScanner.readLine("Enter name or x to exit: ");
            isExist = name.equalsIgnoreCase("x");
            if (!isExist)
                publisherService.sendMessageToMq(name);
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {

        RabbitMqConfig rabbitMqConfig =  new RabbitMqConfigImpl(ConfigsUtils.getConnectionFactory());
        MessagePublisherService publisherService = new MessagePublisherServiceImpl(rabbitMqConfig.getChannel());
        MessagePublisherRunner runner = new MessagePublisherRunner(publisherService, new CustomScanner());
        runner.promoteUserInput();
    }
}
