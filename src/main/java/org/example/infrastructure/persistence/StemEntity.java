package org.example.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.model.StemType;

import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StemEntity {

    @Id
    @Column(name = "stem_id")
    private UUID stemId;

    @Column(name = "stem_stype", nullable = false)
    @Enumerated(EnumType.STRING)
    private StemType stemType;

    @Column(name = "stem_path", nullable = false)
    private String stemPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private TrackEntity track;


}
