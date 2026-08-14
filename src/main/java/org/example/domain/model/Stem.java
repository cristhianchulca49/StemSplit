package org.example.domain.model;


import java.util.UUID;

public record Stem(
        UUID stemId,
        StemType stemType,
        FilePath filePath
) {
    public static Stem create(StemType stemType, FilePath filePath) {
        return new Stem(
                UUID.randomUUID(),
                stemType,
                filePath
        );
    }

    public static Stem reconstitute(UUID stemId, StemType stemType, FilePath filePath) {
        return new Stem(
                stemId,
                stemType,
                filePath
        );
    }
}