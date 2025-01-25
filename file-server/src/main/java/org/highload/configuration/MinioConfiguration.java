package org.highload.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
class MinioConfiguration {

    @Bean
    public MinioClient initMinioClient(MinioConnectionDetails minioConnectionDetails) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioConnectionDetails.url)
                .credentials(minioConnectionDetails.accessKey, minioConnectionDetails.secretKey)
                .build();
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConnectionDetails.bucket).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConnectionDetails.bucket).build());
            }
        } catch (MinioException | GeneralSecurityException | IOException e) {
            throw new RuntimeException("Failed to init minioClient " + e);
        }

        return minioClient;
    }

//    @Bean
//    @ConditionalOnMissingBean(MinioConnectionDetails.class)
//    public MinioConnectionDetails s3ConnectionDetails(MinioProperties minioProperties) {
//        return new MinioConnectionDetailsImpl(minioProperties);
//    }

//    private class MinioConnectionDetailsImpl extends MinioConnectionDetails {
//        public MinioConnectionDetailsImpl(MinioProperties minioProperties) {
//            this.url = minioProperties.endpoint;
//            this.accessKey = minioProperties.accessKey;
//            this.secretKey = minioProperties.secretKey;
//            this.bucket = minioProperties.bucket;
//        }
//    }
}