package org.highload.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.highload.service.ObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.highload.service.StorageService;

@Controller
public class ObjectStorageController {

    private ObjectStorageService objectStorageService;

    @Autowired
    public ObjectStorageController(ObjectStorageService objectStorageService) {
        this.objectStorageService = objectStorageService;
    }

    @GetMapping("/")
    public String listUploadedFiles() {
        return null;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> handleFileRequest(String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//
//        if (file == null)
//            return ResponseEntity.notFound().build();
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        return null;
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        return null;
    }

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return null;
//    }

}
