package org.example.domain.ports.out;

import org.example.domain.event.AudioProcessingEvent;

public interface MessageBrokerPort {
    void publishAudioProcessingRequest(AudioProcessingEvent event);
}
