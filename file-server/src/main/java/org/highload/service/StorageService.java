package org.highload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {

    String store(MultipartFile file);

    String loadAll();

    InputStream load(String fileName);

}

