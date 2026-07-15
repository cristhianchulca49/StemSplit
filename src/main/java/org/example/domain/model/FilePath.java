package org.example.domain.model;

import java.util.UUID;

public record FilePath(String filePath) {

    public static FilePath of(String mainFilePath, UUID id) {
        return new FilePath(mainFilePath + "/" + id.toString());
    }

    public static FilePath reconstitute(String fileName) {
        return new FilePath(fileName);
    }
}
