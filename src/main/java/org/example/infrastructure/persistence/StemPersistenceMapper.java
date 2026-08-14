package org.example.infrastructure.persistence;

import org.example.domain.model.FilePath;
import org.example.domain.model.Stem;
import org.springframework.stereotype.Component;

@Component
public class StemPersistenceMapper {
    public StemEntity toEntity(Stem stem) {
        return StemEntity.builder()
                .stemId(stem.stemId())
                .stemPath(stem.filePath().value())
                .stemType(stem.stemType())
                .build();
    }

    public Stem toDomain(StemEntity stemEntity) {
        return Stem.reconstitute(
                stemEntity.getStemId(),
                stemEntity.getStemType(),
                FilePath.reconstitute(stemEntity.getStemPath())
        );
    }
}
