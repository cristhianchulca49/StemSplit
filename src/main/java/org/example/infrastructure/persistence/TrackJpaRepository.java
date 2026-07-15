package org.example.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface TrackJpaRepository extends JpaRepository<TrackEntity, UUID> {
}