package org.example.domain.event;

import java.io.Serializable;
import java.util.UUID;

public record AudioProcessingEvent(
        UUID trackId,
        String filePath
) implements Serializable {}