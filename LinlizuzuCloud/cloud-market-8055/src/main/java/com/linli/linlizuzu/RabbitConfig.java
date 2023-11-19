package com.xyxy.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Damumu
 */
@Configuration
public class RabbitConfig {
    @Bean("queueChat")
    public Queue queueChat(){
        return QueueBuilder.durable("chat_queue").build();
    }

    @Bean("exchangeChat")
    public Exchange exchangeChat(){
        return ExchangeBuilder.directExchange("chat_exchange").durable(true).build();
    }


}
