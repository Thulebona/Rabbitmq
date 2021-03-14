package com.iqb.messagepublisher.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessagePublisherService {
    void sendMessageToMq(String name) throws IOException, TimeoutException;
}
