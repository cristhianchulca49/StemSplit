package infrastructure.storage;

import org.example.domain.model.FilePath;
import org.example.infrastructure.storage.LocalFileStorageAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LocalFileStorageAdapterTest {

    private LocalFileStorageAdapter adapter;

    @TempDir
    Path tempDirectory;

    @BeforeEach
    void setUp() {
        adapter = new LocalFileStorageAdapter(tempDirectory.toString());
    }

    @Test
    @DisplayName("should save a file and return the correct FilePath")
    void shouldSaveAFileAndReturnTheCorrectFilePath() throws Exception {
        UUID trackId = UUID.randomUUID();
        String fileContent = "Audio binary for the demo";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));

        FilePath filePath = adapter.save(inputStream, trackId);

        assertNotNull(filePath);
        assertEquals(tempDirectory.resolve(trackId.toString()).toString(), filePath.filePath());

        Path expectedPath = tempDirectory.resolve(trackId.toString());
        assertTrue(Files.exists(expectedPath));

        String savedContent = Files.readString(expectedPath, StandardCharsets.UTF_8);
        assertEquals(fileContent, savedContent);
    }


    @Test
    @DisplayName("should return existing file")
    void shouldFindAndReadExistingFile() throws Exception {
        String fileContent = "Audio binary for the demo";
        Path pathFileDemo = tempDirectory.resolve(UUID.randomUUID().toString());
        Files.writeString(pathFileDemo, fileContent, StandardCharsets.UTF_8);

        InputStream foundStream = adapter.find(FilePath.of(pathFileDemo.toString()));

        assertNotNull(foundStream, "File should not be null");

        String fileContentRead = new String(foundStream.readAllBytes(), StandardCharsets.UTF_8);
        assertEquals(fileContent, fileContentRead);
    }

    @Test
    @DisplayName("should delete existing file")
    void shouldDeleteExistingFile() throws Exception {
        Path pathFileDemo = tempDirectory.resolve(UUID.randomUUID().toString());
        Files.writeString(pathFileDemo, "Audio binary for the demo", StandardCharsets.UTF_8);

        adapter.delete(FilePath.of(pathFileDemo.toString()));
        assertFalse(Files.exists(pathFileDemo));
    }
}
