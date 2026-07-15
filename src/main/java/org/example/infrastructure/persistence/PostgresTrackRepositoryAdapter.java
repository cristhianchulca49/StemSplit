package org.example.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.Track;
import org.example.domain.port.TrackRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostgresTrackRepositoryAdapter implements TrackRepositoryPort {

    private final TrackJpaRepository trackJpaRepository;
    private final TrackPersistenceMapper mapper;

    @Override
    public Track save(Track track) {
        TrackEntity trackEntity = mapper.toEntity(track);
        TrackEntity savedTrackEntity = trackJpaRepository.save(trackEntity);
        return mapper.toDomain(savedTrackEntity);
    }

    @Override
    public Optional<Track> findById(UUID id) {
        return trackJpaRepository.findById(id)
                .map(mapper::toDomain);
    }
}
