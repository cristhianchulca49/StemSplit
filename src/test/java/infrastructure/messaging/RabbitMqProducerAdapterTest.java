package org.example.infrastructure.messaging;

import org.example.domain.event.AudioProcessingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RabbitMqProducerAdapterTest {

    @Mock
    private AmqpTemplate amqpTemplate;

    @InjectMocks
    private RabbitMqProducerAdapter rabbitMqProducerAdapter;

    private final String testExchange = "audio.processing.exchange";
    private final String testRoutingKey = "audio.processing.routingkey";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(rabbitMqProducerAdapter, "exchange", testExchange);
        ReflectionTestUtils.setField(rabbitMqProducerAdapter, "routingKey", testRoutingKey);
    }

    @Test
    @DisplayName("should publish a event of AudioProcessingEvent mapped to JSON through RabbitTemplate")
    void shouldPublishAudioProcessingRequestSuccessfully() {
        UUID trackId = UUID.randomUUID();
        String filePath = "/uploads/audio-sample.wav";
        AudioProcessingEvent event = new AudioProcessingEvent(trackId, filePath);

        rabbitMqProducerAdapter.publishAudioProcessingRequest(event);

        verify(amqpTemplate, times(1)).convertAndSend(testExchange, testRoutingKey, event);
    }
}