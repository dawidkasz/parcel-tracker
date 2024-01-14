package com.parcel.reportgenerator.infrastructure.persistence.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfig {

    @Bean
    public MinioClient reportStorageClient(@Value("${spring.minio.port}") long port,
                                           @Value("${spring.minio.host}") String host,
                                           @Value("${spring.minio.username}") String user,
                                           @Value("${spring.minio.password}") String password) {
        return MinioClient.builder()
                .endpoint("http://%s:%s".formatted(host, port))
                .credentials(user, password)
                .build();
    }
}
