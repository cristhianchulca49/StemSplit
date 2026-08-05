package org.example.infrastructure.persistence;

import org.example.domain.model.FileName;
import org.example.domain.model.FilePath;
import org.example.domain.model.Track;
import org.springframework.stereotype.Component;

@Component
public class TrackPersistenceMapper {

    public TrackEntity toEntity(Track track) {
        return TrackEntity.builder()
                .trackId(track.getTrackId())
                .fileName(track.getFileName().value())
                .filePath(track.getFilePath().value())
                .status(track.getStatus())
                .build();
    }

    public Track toDomain(TrackEntity trackEntity) {
        return Track.reconstitute(
                trackEntity.getTrackId(),
                FileName.of(trackEntity.getFileName()),
                FilePath.reconstitute(trackEntity.getFilePath()),
                trackEntity.getStatus(),
                trackEntity.getStems()
        );
    }
}
