package com.parcel.reportgenerator.infrastructure.persistence.minio;

import com.parcel.reportgenerator.domain.ReportDocument;
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
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class MinioReportRepository {
    private final MinioClient reportStorageClient;
    private final static String BUCKET_NAME = "test-bucket";


    public Optional<String> saveFile(ReportDocument document) {
        try (var data = new ByteArrayInputStream(document.document())) {
            try {
                var response = reportStorageClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(BUCKET_NAME)
                                .object(document.id().uuid().toString())
                                .stream(data, document.document().length, -1)
                                .contentType("application/pdf")
                                .userMetadata(createMetadata(document))
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

    private Map<String, String> createMetadata(ReportDocument document) {
        return Map.of(
                "DESCRIPTION", document.description(),
                "HASH", String.valueOf(document.contentHash())
        );
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
