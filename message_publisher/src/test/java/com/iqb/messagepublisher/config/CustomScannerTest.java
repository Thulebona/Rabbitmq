package com.iqb.messagepublisher.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomScannerTest {
    @Mock
    private CustomScanner scannerMock;

    @Test
    public void promoteUserInputWithMessage() throws IOException {
        when(scannerMock.readLine(anyString())).thenReturn("John");
        when(scannerMock.readLine("Enter surname")).thenReturn("Zulu");

        assertEquals("John", scannerMock.readLine("Enter name"));
        assertEquals("Zulu", scannerMock.readLine("Enter surname"));
        verify(scannerMock, times(2)).readLine(anyString());
        verify(scannerMock, times(1)).readLine("Enter surname");
    }


}