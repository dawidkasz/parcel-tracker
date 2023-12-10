package com.parcel.reportgenerator.client;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class StorageClient {
    private final MinioClient reportStorageClient;
    private final static String BUCKET_NAME = "test-bucket";


    public Optional<String> saveFile(String fileName, String content) {
        try (var data = new ByteArrayInputStream(content.getBytes())) {
            try {
                var response = reportStorageClient.putObject(
                        PutObjectArgs.builder().bucket(BUCKET_NAME).object(fileName).stream(
                                        data, content.getBytes().length, -1)
                                .contentType("text/plain")
                                .build());
                return Optional.ofNullable(response.object());
            } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
                log.error("Saving file failed");
                return Optional.empty();
            }
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public Optional<byte[]> getFile(String fileName) {
        try (var stream = reportStorageClient.getObject(
                GetObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(fileName)
                        .build())) {
            return Optional.of(stream.readAllBytes());
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            log.error("Retrieving file failed");
            return Optional.empty();
        }
    }
}
