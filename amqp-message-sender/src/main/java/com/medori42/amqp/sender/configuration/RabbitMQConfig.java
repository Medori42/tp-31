package com.medori42.amqp.sender.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ configuration class for the AMQP Message Sender Service.
 * Configures exchanges, queues, bindings, and message converters.
 *
 * @author Medori42
 * @version 1.0.0
 */
@Configuration
public class RabbitMQConfig {

    /**
     * Name of the exchange for user messages.
     */
    @Value("${rabbitmq.exchange.name:user.exchange}")
    private String exchangeName;

    /**
     * Name of the queue for user messages.
     */
    @Value("${rabbitmq.queue.name:user.queue}")
    private String queueName;

    /**
     * Routing key for user messages.
     */
    @Value("${rabbitmq.routing.key:user.routingkey}")
    private String routingKey;

    /**
     * Creates a direct exchange for routing user messages.
     *
     * @return DirectExchange instance
     */
    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(exchangeName);
    }

    /**
     * Creates a durable queue for user messages.
     *
     * @return Queue instance
     */
    @Bean
    public Queue userQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    /**
     * Creates a binding between the queue and exchange using the routing key.
     *
     * @param userQueue the queue to bind
     * @param userExchange the exchange to bind to
     * @return Binding instance
     */
    @Bean
    public Binding userBinding(Queue userQueue, DirectExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with(routingKey);
    }

    /**
     * Creates a Jackson JSON message converter for serializing messages.
     *
     * @return MessageConverter instance using Jackson for JSON
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Creates a RabbitTemplate configured with JSON message conversion.
     *
     * @param connectionFactory the connection factory for RabbitMQ
     * @return RabbitTemplate instance
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
