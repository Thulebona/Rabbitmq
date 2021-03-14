package com.iqb.messagepublisher.service;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.iqb.messagepublisher.config.RabbitMqConfig;
import com.iqb.messagepublisher.config.RabbitMqConfigImpl;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MessagePublisherServiceTest {

    ConnectionFactory factory;
    MessagePublisherService publisherService;

    @BeforeEach
    void init() throws IOException, TimeoutException {
        factory =  new MockConnectionFactory();
        Channel channel = new RabbitMqConfigImpl(factory).getChannel();
        publisherService =  new MessagePublisherServiceImpl(channel);
    }

    @Test
    public void service_checkDependencyInject() {
        assertNotNull(publisherService);
    }

    @Test
    public void service_testSendMessageToMqTest(@Mock MessagePublisherService publisherServiceMock) throws IOException, TimeoutException {
        publisherService.sendMessageToMq("John");
        doNothing().when(publisherServiceMock).sendMessageToMq(anyString());
        publisherServiceMock.sendMessageToMq("John");
        verify(publisherServiceMock, times(1)).sendMessageToMq(anyString());
    }

}