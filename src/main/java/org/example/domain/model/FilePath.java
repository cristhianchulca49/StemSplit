package org.example.domain.model;

public record FilePath(String filePath) {

    public static FilePath of(String filePath) {
        return new FilePath(filePath);
    }

    public static FilePath reconstitute(String fileName) {
        return new FilePath(fileName);
    }
}
