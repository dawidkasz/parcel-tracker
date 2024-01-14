package com.parcel.reportgenerator.infrastructure.pdf;

import com.parcel.reportgenerator.domain.*;
import com.parcel.reportgenerator.infrastructure.persistence.minio.MinioReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class PdfReportGenerator implements ReportGenerator {

    private final MinioReportRepository storageClient;
    private final PdfReportDrawerService drawerService;
    private final ContentHashProvider hashProvider;

    private static String createFileName(String query) {
        return "report-" + query.hashCode();
    }

    public Optional<byte[]> getReport(String name) {
        return storageClient.getFile(name);
    }

    public Optional<ReportDocument> generateReport(ReportRequest request, List<Parcel> parcels) {
        var id = ReportId.nextIdentity();
        try (var stream = new ByteArrayOutputStream()) {
            try (var bufferedStream = new BufferedOutputStream(stream)) {
                drawerService.draw(parcels, bufferedStream);
                bufferedStream.flush();
                log.info("Created report");
                var data = stream.toByteArray();
                return Optional.of(new ReportDocument(id, request.query(), data, hashProvider.getContentHash(parcels), Instant.now()));
            }
        } catch (IOException e) {
            log.warn("Error when generating report");
            return Optional.empty();
        }
    }
}
