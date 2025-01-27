package org.highload.controller;

import org.highload.service.ObjectStorageService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/storage")
public class ObjectStorageController {

    private final ObjectStorageService objectStorageService;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> listUploadedFiles() {
        try {
            return ResponseEntity.ok(objectStorageService.loadAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{fileName:.+}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<InputStream> handleFileRequest(@PathVariable String fileName) {
        try {
            InputStream resource = objectStorageService.load(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(
                    ContentDisposition.attachment()
                            .filename(fileName)
                            .build()
            );
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> handleFileUpload(
            @Validated @RequestParam("file")MultipartFile file
    ) {
        try {
            return ResponseEntity.ok(objectStorageService.store(file));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
