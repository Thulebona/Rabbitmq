package com.iqb.messagepublisher.config;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class ConfigsUtils {

    public static String queueName;
    public static String exchangeName;
    public static String routingKey;
    public static String queueHost;
    public static int queuePort;
    public static String queueUser;
    public static String queuePass;

    static {
        try {
            Configurations configurations = new Configurations();
            PropertiesConfiguration configs = configurations.properties(new File("config.properties"));

            queueName = configs.getString("ibq.rabbitmq.queue.name");
            exchangeName = configs.getString("ibq.rabbitmq.queue.exchange.name");
            routingKey = configs.getString("ibq.rabbitmq.queue.routingKey");
            queueHost = configs.getString("ibq.rabbitmq.queue.host");
            queuePort = configs.getInt("ibq.rabbitmq.queue.port");
            queueUser = configs.getString("ibq.rabbitmq.queue.username");
            queuePass = configs.getString("ibq.rabbitmq.queue.password");
        } catch (ConfigurationException e) {
            throw new RuntimeException("Error reading config file", e);
        }
    }

    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueHost);
        factory.setPort(queuePort);
        factory.setUsername(queueUser);
        factory.setPassword(queuePass);
        return factory;
    }
}
