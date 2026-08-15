package org.example.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${app.rabbitmq.queue}")
    private String queueName;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    @Bean
    public Queue audioProcessingQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange audioProcessingExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue audioProcessingQueue, TopicExchange audioProcessingExchange) {
        return BindingBuilder
                .bind(audioProcessingQueue)
                .to(audioProcessingExchange)
                .with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}