package com.medori42.amqp.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the AMQP Message Sender Service.
 * This Spring Boot application provides REST APIs for publishing messages to RabbitMQ.
 *
 * @author Medori42
 * @version 1.0.0
 */
@SpringBootApplication
public class AmqpMessageSenderApplication {

    /**
     * Main entry point for the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AmqpMessageSenderApplication.class, args);
    }
}
