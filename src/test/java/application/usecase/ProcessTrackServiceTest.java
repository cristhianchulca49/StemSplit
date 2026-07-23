package application.usecase;

import org.example.application.usecase.ProcessTrackService;
import org.example.domain.model.FileName;
import org.example.domain.model.FilePath;
import org.example.domain.model.Status;
import org.example.domain.model.Track;
import org.example.domain.ports.out.FileStoragePort;
import org.example.domain.ports.out.TrackRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProcessTrackServiceTest {
    @Mock
    private FileStoragePort storage;

    @Mock
    private TrackRepositoryPort repository;

    @InjectMocks
    private ProcessTrackService service;

  @Test
  @DisplayName("should create a new object Track")
    void shouldCreateATrack(){
      String fileName = "Song1";
      String songContent = "Content for the demo";
      InputStream song = new ByteArrayInputStream(songContent.getBytes(StandardCharsets.UTF_8));
      String filePathDemo = "Path for the demo";
      UUID expectedId = UUID.randomUUID();
      Track mockSavedTrack = Track.create(expectedId, FileName.of(fileName), FilePath.of(filePathDemo));

      when(storage.save(any(InputStream.class), any(UUID.class))).thenReturn(FilePath.of(filePathDemo));
      when(repository.save(any(Track.class))).thenReturn(mockSavedTrack);

      Track trackCreated = service.execute(song, fileName);

      assertNotNull(trackCreated.getId());
      assertEquals(expectedId, trackCreated.getId());
      assertEquals(fileName, trackCreated.getFileName().fileName());
      assertEquals(Status.PENDING, trackCreated.getStatus());

  }
}
