package org.example.infrastructure.persistence;

import lombok.AllArgsConstructor;
import org.example.domain.model.FileName;
import org.example.domain.model.FilePath;
import org.example.domain.model.Stem;
import org.example.domain.model.Track;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TrackPersistenceMapper {
    private StemPersistenceMapper stemMapper;

    public TrackEntity toEntity(Track track) {
        return TrackEntity.builder()
                .trackId(track.getTrackId())
                .fileName(track.getFileName().value())
                .filePath(track.getFilePath().value())
                .status(track.getStatus())
                .build();
    }

    public Track toDomain(TrackEntity trackEntity) {
        Set<Stem> stems = Optional.ofNullable(trackEntity.getStems())
                .orElseGet(Collections::emptySet)
                .stream()
                .map(stemMapper::toDomain)
                .collect(Collectors.toSet());
        return Track.reconstitute(
                trackEntity.getTrackId(),
                FileName.of(trackEntity.getFileName()),
                FilePath.reconstitute(trackEntity.getFilePath()),
                trackEntity.getStatus(),
                stems
        );
    }
}
