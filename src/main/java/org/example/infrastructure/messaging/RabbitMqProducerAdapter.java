package org.example.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Main;
import org.example.domain.event.AudioProcessingEvent;
import org.example.domain.ports.out.MessageBrokerPort;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMqProducerAdapter implements MessageBrokerPort {
    private final AmqpTemplate amqpTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    @Override
    public void publishAudioProcessingRequest(AudioProcessingEvent event) {
        log.info("Publishing event of processing to RabbitMQ for trackId: {}", event.trackId());
        amqpTemplate.convertAndSend(exchange, routingKey, event);
        log.info("Event published successfully for trackId: {}", event.trackId());
    }
}
