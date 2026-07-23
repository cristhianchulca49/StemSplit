package org.example.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.example.application.usecase.ProcessTrackService;
import org.example.domain.model.Track;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final ProcessTrackService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Track> createTask(
            @RequestParam("audio") MultipartFile audio){

        if (audio.isEmpty()) {
            throw new RuntimeException("The uploaded file is empty.");
        }

        try {
            Track trackCreated = service.execute(audio.getInputStream(), audio.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.CREATED).body(trackCreated);
        }catch (Exception e){
            throw new RuntimeException("Error creating Track");
        }
    }

}