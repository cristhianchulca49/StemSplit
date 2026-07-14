package org.example.domain.model;

import java.util.UUID;

public class Track {
    private final UUID id;
    private final FileName fileName;
    private final FilePath filePath;
    private final Status status;

    private Track(UUID id, FileName fileName, FilePath filePath, Status status) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.status = status;
    }

    public UUID getId() {
        return id;
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