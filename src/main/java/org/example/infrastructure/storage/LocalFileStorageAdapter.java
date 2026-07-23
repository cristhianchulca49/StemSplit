package org.example.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.FilePath;
import org.example.domain.ports.out.FileStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class LocalFileStorageAdapter implements FileStoragePort{

    private final String basePath;

    public LocalFileStorageAdapter(@Value("${storage.local.default-dir}") String basePath) {
        this.basePath = basePath;
    }

    @Override
    public FilePath save(InputStream inputStream, UUID trackId) {
        try{
            Path dir = Path.of(basePath);
            Files.createDirectories(dir);

            Path target = dir.resolve(trackId.toString());

            Files.copy(
                    inputStream,
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return FilePath.of(target.toString());

        }catch (Exception e){
            throw new RuntimeException("Failed to save file" + e.getMessage(), e);
        }
    }

    @Override
    public InputStream find(FilePath filePath) {
        try{
            return Files.newInputStream(Path.of(filePath.value()));
        }catch (Exception e){
            throw new RuntimeException("Failed to read file" + e.getMessage(), e);
        }
    }

    @Override
    public void delete(FilePath filePath) {
        try {
            Files.deleteIfExists(Path.of(filePath.value()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file" + e.getMessage(), e);
        }
    }
}
