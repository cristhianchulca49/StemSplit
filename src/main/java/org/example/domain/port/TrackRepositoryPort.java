package org.example.domain.port;

import org.example.domain.model.Track;

import java.util.UUID;

public interface TrackRepositoryPort {

    Track save(Track track);

    Track findById(UUID id);
}
