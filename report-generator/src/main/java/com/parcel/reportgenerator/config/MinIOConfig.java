package com.parcel.reportgenerator.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfig {

    @Bean
    public MinioClient reportStorageClient(){
        return MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("minio-root-user", "minio-root-password")
                .build();
    }
}
