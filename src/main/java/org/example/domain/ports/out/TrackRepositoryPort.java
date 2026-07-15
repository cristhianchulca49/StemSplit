package org.example.domain.ports.out;

import org.example.domain.model.Track;

import java.util.Optional;
import java.util.UUID;

public interface TrackRepositoryPort {

    Track save(Track track);

    Optional<Track> findById(UUID id);
}
