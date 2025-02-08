package org.highload.controller;

import org.highload.service.ObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Controller
@MessageMapping("/file")
public class WebSocketController {
    @Autowired
    private ObjectStorageService objectStorageService;

    @MessageMapping("list")
    @SendTo({"/topic/file/urls"})
    @SendToUser("/queue/file-list")
    public String getAllObjects() {
        return objectStorageService.loadAll();
    }

    @MessageMapping("download.{fileName}")
    @SendTo("/topic/file/data")
    @SendToUser("/queue/file")
    public InputStream getFile(@DestinationVariable String fileName) {
        return objectStorageService.load(fileName);
    }

    @MessageMapping("upload")
    @SendTo("/topic/file/url")
    @SendToUser("/queue/url")
    public String postFile(@Payload MultipartFile file) {
        return objectStorageService.store(file);
    }
}
