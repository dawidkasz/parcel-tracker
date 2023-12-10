package com.parcel.reportgenerator.service;

import com.parcel.reportgenerator.client.StorageClient;
import com.parcel.reportgenerator.domain.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ReportService {

    private final StorageClient storageClient;

    public Optional<String> createReport(Report report) {
        return storageClient.saveFile(report.filename(), report.content());
    }

    public Optional<byte[]> getReport(String name) {
        return storageClient.getFile(name);
    }
}
