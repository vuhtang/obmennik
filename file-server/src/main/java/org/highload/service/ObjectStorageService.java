package org.highload.service;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.highload.datasource.MinioAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ObjectStorageService implements StorageService {
    private MinioAdapter minioAdapter;

    @Override
    public void store(MultipartFile file) {
        try {
            minioAdapter.putObject(file.getInputStream(), file.getOriginalFilename());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<URI> loadAll() {
//        return minioAdapter.;
        return null;
    }

    @Override
    public MultipartFile load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
