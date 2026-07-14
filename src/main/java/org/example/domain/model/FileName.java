package org.example.domain.model;


public record FileName(String fileName) {

    public static FileName of(String fileName) {
        return new FileName(fileName);
    }
}
