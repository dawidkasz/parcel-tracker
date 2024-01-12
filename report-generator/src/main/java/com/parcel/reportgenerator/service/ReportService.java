package com.parcel.reportgenerator.service;

import com.netflix.discovery.converters.Auto;
import com.parcel.reportgenerator.client.PackageClient;
import com.parcel.reportgenerator.client.StorageClient;
import com.parcel.reportgenerator.domain.Parcel;
import com.parcel.reportgenerator.domain.Report;
import com.parcel.reportgenerator.drawer.ReportDrawerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class ReportService {

    private final StorageClient storageClient;
    private final ReportDrawerService drawerService;
    private final PackageClient packageClient;

    public Optional<String> createReport(Report report) {
        var parcels = packageClient.getParcels("");
        if (parcels.isEmpty()) {
            log.debug("No parcel found");
            return Optional.empty();
        }
        var pdfReport = generateReport(parcels);
        return pdfReport.flatMap(pdf -> storageClient.saveFile(createFileName(report.query()), pdf));
    }

    private static String createFileName(String query) {
        return "report-" + query.hashCode();
    }

    public Optional<byte[]> getReport(String name) {
        return storageClient.getFile(name);
    }

    private Optional<byte[]> generateReport(List<Parcel> parcels) {
        try (var stream = new ByteArrayOutputStream()) {
            try (var bufferedStream = new BufferedOutputStream(stream)) {
                drawerService.draw(parcels, bufferedStream);
                bufferedStream.flush();
                log.info("Created report");
                return Optional.of(stream.toByteArray());
            }
        } catch (IOException e) {
            log.error("Error when generating report");
            return Optional.empty();
        }
    }
}
