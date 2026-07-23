package org.example.infrastructure.rest;

import org.example.application.usecase.ProcessTrackService;
import org.example.domain.model.FileName;
import org.example.domain.model.FilePath;
import org.example.domain.model.Track;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackController.class)
public class TrackControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private ProcessTrackService service;

    @Test
    void shouldCreateAndReturnATrack() throws Exception {
        String fileName = "Song1.mp3";
        Track trackCreated = Track.create(UUID.randomUUID(), FileName.of(fileName), FilePath.of("path"));

        MockMultipartFile mockSong = new MockMultipartFile(
                "audio",
                fileName,
                "audio/mpeg",
                "Content for the demo".getBytes()
        );

        given(service.execute(any(InputStream.class), eq(fileName))).willReturn(trackCreated);

        mock.perform(multipart("/api/v1/tracks")
                        .file(mockSong))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fileName.value").value(fileName));
    }
}