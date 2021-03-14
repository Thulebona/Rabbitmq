package com.iqb.messagereceiver.listener;

public interface MessageReceiverListener {
    void readingMessageFromMqAsync();

    void printMessage(byte[] data);
}
