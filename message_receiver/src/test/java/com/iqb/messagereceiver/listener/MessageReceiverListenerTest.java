package com.iqb.messagereceiver.listener;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.iqb.messagereceiver.config.RabbitMqConfigImpl;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class MessageReceiverListenerTest {

    MessageReceiverListener receiverListener;
    @Mock
    MessageReceiverListener receiverListenerMock;

    @BeforeEach
    void setUp() throws IOException, TimeoutException {
        ConnectionFactory factory = new MockConnectionFactory();
        Channel channel = new RabbitMqConfigImpl(factory).getChannel();
        receiverListener = new MessageReceiverListenerImpl(channel);

    }

    @Test
    void listener_testReadingMessageFromMqAsync() {
        receiverListener.readingMessageFromMqAsync();

        doNothing().when(receiverListenerMock).readingMessageFromMqAsync();
        receiverListenerMock.readingMessageFromMqAsync();
        verify(receiverListenerMock, times(1)).readingMessageFromMqAsync();
    }

    @Test
    void listener_testPrintMessage() {
        byte[] expected = "My name is, Shaka".getBytes(StandardCharsets.UTF_8);
        receiverListener.printMessage(expected);

        doNothing()
                .when(receiverListenerMock)
                .printMessage(any(byte[].class));

        receiverListenerMock.printMessage(expected);
        verify(receiverListenerMock,times(1)).printMessage(expected);
    }
}