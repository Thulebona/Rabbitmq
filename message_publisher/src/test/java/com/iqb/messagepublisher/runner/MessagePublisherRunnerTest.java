package com.iqb.messagepublisher.runner;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.iqb.messagepublisher.MessagePublisherRunner;
import com.iqb.messagepublisher.config.CustomScanner;
import com.iqb.messagepublisher.config.RabbitMqConfigImpl;
import com.iqb.messagepublisher.service.MessagePublisherServiceImpl;
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
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MessagePublisherRunnerTest {

    @Mock
    CustomScanner scannerMock;
    MessagePublisherRunner publisherRunner;
    Channel channel;


    @BeforeEach
    void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new MockConnectionFactory();
        channel = new RabbitMqConfigImpl(factory).getChannel();
        publisherRunner = new MessagePublisherRunner(new MessagePublisherServiceImpl(channel), scannerMock);
    }


    @Test
    public void runner_verifyPromoteUserInputExecution(@Mock MessagePublisherRunner publisherRunnerMock) throws IOException, TimeoutException {
        doThrow(TimeoutException.class)
                .doThrow(IOException.class)
                .when(publisherRunnerMock).promoteUserInput();

        assertThrows(TimeoutException.class, publisherRunnerMock::promoteUserInput);
        assertThrows(IOException.class, publisherRunnerMock::promoteUserInput);

        doNothing().when(publisherRunnerMock).promoteUserInput();
        publisherRunnerMock.promoteUserInput();

        verify(publisherRunnerMock, times(3)).promoteUserInput();
    }

    @Test
    public void runner_testPromoteUserInput() throws IOException, TimeoutException {
        when(scannerMock.readLine(anyString()))
                .thenReturn("Shaka")
                .thenReturn("John")
                .thenReturn("x");

        publisherRunner.promoteUserInput();
        verify(scannerMock, times(3)).readLine(anyString());
    }


}