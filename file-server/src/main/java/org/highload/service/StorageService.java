package org.highload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    void store(MultipartFile file);

    List<URI> loadAll();

    MultipartFile load(String filename);

    void deleteAll();

}

