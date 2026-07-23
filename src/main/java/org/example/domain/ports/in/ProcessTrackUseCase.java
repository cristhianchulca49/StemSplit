package org.example.domain.ports.in;

import org.example.domain.model.Track;

import java.io.InputStream;

public interface ProcessTrackUseCase {

    Track execute(InputStream inputStream, String fileName);
}