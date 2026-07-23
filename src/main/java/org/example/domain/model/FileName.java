package org.example.domain.model;


public record FileName(String value) {

    public static FileName of(String fileName) {
        return new FileName(fileName);
    }
}
