package org.example.domain.ports.out;

import org.example.domain.model.FilePath;

import java.io.InputStream;
import java.util.UUID;

public interface FileStoragePort {
    FilePath save(InputStream inputStream, UUID trackId);

    InputStream find(FilePath filePath);

    void delete(FilePath filePath);
}
