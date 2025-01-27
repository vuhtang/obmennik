package org.highload.datasource;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import io.minio.messages.Item;
import org.highload.configuration.MinioConnectionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MinioAdapter {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioConnectionDetails minioConnectionDetails;


    public String getPresignedObjectUrl(String name) throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException {
        var args = GetPresignedObjectUrlArgs.builder()
                .bucket(minioConnectionDetails.getBucket())
                .object(name)
                .expiry(1, TimeUnit.DAYS)
                .method(Method.GET)
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }

    public Iterable<Result<Item>> getAllObjectsList() {
        return minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(minioConnectionDetails.getBucket())
                        .recursive(true)
                        .build()
        );
    }

    public InputStream getObject(String name) throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioConnectionDetails.getBucket())
                        .object(name)
                        .build()
        );
    }

    public void putObject(InputStream file, String fileName) throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException {
        minioClient.putObject(
                PutObjectArgs.builder().
                        bucket(minioConnectionDetails.getBucket())
                        .object(fileName)
                        .stream(file, -1, 10485760)
                        .build()
        );
    }

    public void deleteObject(String fileName) throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(minioConnectionDetails.getBucket())
                        .object(fileName)
                        .build()
        );
    }
}
