package org.example.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.model.StemType;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StemEntity {

    @Id
    @Column(name = "id")
    @OneToMany()
    private UUID steamId;

    @Column(name = "stem_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StemType stemType;

    @Column(name =  "stem_path", nullable = false)
    private String stemPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private TrackEntity track;


}
