package org.highload.datasource;

import io.minio.MinioClient;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.highload.configuration.MinioConnectionDetails;
import org.springframework.stereotype.Service;

@Service
public class MinioAdapter {
    private MinioClient minioClient;
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
                PutObjectArgs.builder().bucket(minioConnectionDetails.getBucket()).object(fileName).stream(
                        file, -1, 10485760
                )
//                .contentType("image/jpg")
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
