package org.example.domain.model;

import java.util.UUID;

public class Track {
    private final UUID trackId;
    private final FileName fileName;
    private final FilePath filePath;
    private final Status status;

    private Track(UUID trackId, FileName fileName, FilePath filePath, Status status) {
        this.trackId = trackId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.status = status;
    }

    public static Track create (UUID trackId, FileName fileName, FilePath filePath){
        return new Track(
                trackId,
                fileName,
                filePath,
                Status.PENDING
        );
    }

    public static Track reconstitute(UUID id, FileName fileName, FilePath filePath, Status status) {
        return new Track(id, fileName, filePath, status);
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
}