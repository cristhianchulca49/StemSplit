package org.example.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Track {
    private final UUID trackId;
    private final FileName fileName;
    private final FilePath filePath;
    private final Status status;
    private final Set<Stem> stems;

    private Track(UUID trackId, FileName fileName, FilePath filePath, Status status, Set<Stem> stems) {
        this.trackId = trackId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.status = status;
        this.stems = stems;
    }

    public static Track create (UUID trackId, FileName fileName, FilePath filePath){
        return new Track(
                trackId,
                fileName,
                filePath,
                Status.PENDING,
                new HashSet<>());
    }

    public static Track reconstitute(UUID id, FileName fileName, FilePath filePath, Status status, Set<Stem> stems) {
        return new Track(id, fileName, filePath, status, stems);
    }

    public UUID getTrackId() {
        return trackId;
    }

    public FileName getFileName() {
        return fileName;
    }

    public FilePath getFilePath() {
        return filePath;
    }

    public Status getStatus() {
        return status;
    }

    public Set<Stem> getStems() {
        return Collections.unmodifiableSet(stems);
    }
}