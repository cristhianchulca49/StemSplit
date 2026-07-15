package infrastructure.persistence;

import org.example.domain.model.FileName;
import org.example.domain.model.FilePath;
import org.example.domain.model.Status;
import org.example.domain.model.Track;
import org.example.infrastructure.persistence.PostgresTrackRepositoryAdapter;
import org.example.infrastructure.persistence.TrackEntity;
import org.example.infrastructure.persistence.TrackJpaRepository;
import org.example.infrastructure.persistence.TrackPersistenceMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostgresTrackRepositoryAdapterTest {
    @Mock
    private TrackJpaRepository trackJpaRepository;

    @Mock
    private TrackPersistenceMapper trackPersistenceMapper;

    @InjectMocks
    private PostgresTrackRepositoryAdapter postgresTrackRepositoryAdapter;

    @Nested
    @DisplayName("save method")
    class SaveMethodTests {

        @Test
        @DisplayName("should mapper, save and return a domain track")
        void shouldSaveTrackSuccessfully() {
            Track trackInput =  mock(Track.class);
            TrackEntity trackEntity = TrackEntity.builder().build();
            TrackEntity savedEntity = TrackEntity.builder().build();
            Track savedTrack = Track.reconstitute(UUID.randomUUID(), FileName.of("test.mp3"), FilePath.reconstitute("/path/to/test.mp3"), Status.PENDING);

            when(trackPersistenceMapper.toEntity(trackInput)).thenReturn(trackEntity);
            when(trackJpaRepository.save(trackEntity)).thenReturn(savedEntity);
            when(trackPersistenceMapper.toDomain(savedEntity)).thenReturn(savedTrack);

            Track result = postgresTrackRepositoryAdapter.save(trackInput);

            assertNotNull(result);
            assertEquals(savedTrack, result);

            verify(trackPersistenceMapper).toEntity(trackInput);
            verify(trackJpaRepository).save(trackEntity);
            verify(trackPersistenceMapper).toDomain(savedEntity);

        }
    }

    @Nested
    @DisplayName("findById method")
    class FindByIdMethodTests {

        @Test
        @DisplayName("should return a domain track when found by id")
        void shouldFindTrackByIdWhenTrackExists() {
            Track trackInput = Track.reconstitute(UUID.randomUUID(), FileName.of("test.mp3"), FilePath.reconstitute("/path/to/test.mp3"), Status.PENDING);
            TrackEntity trackEntity = TrackEntity.builder().build();

            when(trackJpaRepository.findById(trackInput.getId())).thenReturn(Optional.of(trackEntity));
            when(trackPersistenceMapper.toDomain(trackEntity)).thenReturn(trackInput);

            Optional<Track> result = postgresTrackRepositoryAdapter.findById(trackInput.getId());

            assertNotNull(result);
            assertEquals(trackInput, result.get());

            verify(trackJpaRepository).findById(trackInput.getId());
            verify(trackPersistenceMapper).toDomain(trackEntity);

        }

        @Test
        @DisplayName("should return empty when track not found by id")
        void shouldReturnEmptyWhenTrackNotFoundById() {
            UUID trackId = UUID.randomUUID();

            when(trackJpaRepository.findById(trackId)).thenReturn(Optional.empty());
            Optional<Track> result = postgresTrackRepositoryAdapter.findById(trackId);

            assertFalse(result.isPresent());

            verify(trackJpaRepository).findById(trackId);
            verifyNoMoreInteractions(trackPersistenceMapper);
        }
    }
}
