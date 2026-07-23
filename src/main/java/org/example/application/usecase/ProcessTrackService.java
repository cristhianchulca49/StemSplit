package org.example.application.usecase;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.FileName;
import org.example.domain.model.FilePath;
import org.example.domain.model.Track;
import org.example.domain.ports.in.ProcessTrackUseCase;
import org.example.domain.ports.out.FileStoragePort;
import org.example.domain.ports.out.TrackRepositoryPort;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessTrackService implements ProcessTrackUseCase {
    private final FileStoragePort fileStorage;
    private final TrackRepositoryPort trackRepository;

    @Override
    public Track execute(InputStream inputStream, String fileName) {

        UUID trackId = UUID.randomUUID();
        FilePath filePath = fileStorage.save(inputStream, trackId);
        Track newTrack = Track.create(trackId, FileName.of(fileName), filePath);
        return trackRepository.save(newTrack);
    }

}