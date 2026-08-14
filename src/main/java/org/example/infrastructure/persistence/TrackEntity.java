package org.example.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.model.Status;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tracks")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackEntity {
    @Id
    @Column(name = "track_id", nullable = false, updatable = false, unique = true)
    private UUID trackId;

    @Column(name = "file_name", length = 255, updatable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false, updatable = false)
    private String filePath;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<StemEntity> stems;

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void addStem(StemEntity stem) {
        this.stems.add(stem);
        stem.setTrack(this);
    }
}
