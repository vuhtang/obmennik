package org.highload.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.highload.service.ObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Controller
@MessageMapping("/file")
public class StompController {
    @Autowired
    private ObjectStorageService objectStorageService;

    @MessageMapping("list")
    @SendTo({"/topic/file/urls"})
    public String getAllObjects() {
        return objectStorageService.loadAll();
    }

    @MessageMapping("download.{fileName}")
    @SendTo("/topic/file/data")
    public String getFile(@DestinationVariable String fileName) throws IOException {
        InputStream stream = objectStorageService.load(fileName);
        return Base64.getEncoder().encodeToString(stream.readAllBytes());
    }

    @MessageMapping("upload.{fileName}")
    @SendTo("/topic/file/url")
    public String postFile(@Payload String fileBase64, @DestinationVariable String fileName) {
        InputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(fileBase64));
        return objectStorageService.storeInputStream(inputStream, fileName);
    }
}
