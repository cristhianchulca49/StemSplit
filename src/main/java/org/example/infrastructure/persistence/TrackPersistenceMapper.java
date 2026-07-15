package org.example.infrastructure.persistence;

import org.example.domain.model.FileName;
import org.example.domain.model.FilePath;
import org.example.domain.model.Track;
import org.springframework.stereotype.Component;

@Component
public class TrackPersistenceMapper {

    public TrackEntity toEntity(Track track) {
        return TrackEntity.builder()
                .id(track.getId())
                .fileName(track.getFileName().fileName())
                .filePath(track.getFilePath().filePath())
                .status(track.getStatus())
                .build();
    }

    public Track toDomain(TrackEntity trackEntity) {
        return Track.reconstitute(
                trackEntity.getId(),
                FileName.of(trackEntity.getFileName()),
                FilePath.reconstitute(trackEntity.getFilePath()),
                trackEntity.getStatus()
        );
    }
}
