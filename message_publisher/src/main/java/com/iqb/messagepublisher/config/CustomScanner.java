package com.iqb.messagepublisher.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomScanner {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public String readLine(String message) throws IOException {
        System.out.println(message);
        return this.reader.readLine();
    }
}
