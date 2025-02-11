package org.highload.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.highload.datasource.MinioAdapter;
import org.highload.dto.ObjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ObjectStorageService implements StorageService {
    @Autowired
    private final MinioAdapter minioAdapter;
    private final ObjectMapper objectMapper;

    @Override
    public String store(MultipartFile file) throws RuntimeException {
        try {
            minioAdapter.putObject(file.getInputStream(), file.getOriginalFilename());
            return minioAdapter.getPresignedObjectUrl(file.getOriginalFilename());
        } catch (MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String storeInputStream(InputStream inputStream, String filename) {
        try {
            minioAdapter.putObject(inputStream, filename);
            return minioAdapter.getPresignedObjectUrl(filename);
        } catch (MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String loadAll() throws RuntimeException {
        List<ObjectDto> list = new ArrayList<>();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            var allObjects = minioAdapter.getAllObjectsList();
            for (Result<Item> objectResult : allObjects) {
                Item item = objectResult.get();
                ObjectDto objectDto = ObjectDto.map(item, minioAdapter.getPresignedObjectUrl(item.objectName()));
                list.add(objectDto);
            }
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load list of objects, cause: " + e);
        }
    }

    @Override
    public InputStream load(String fileName) throws RuntimeException {
        try {
            return minioAdapter.getObject(fileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load object to storage, cause: " + e);
        }
    }
}
