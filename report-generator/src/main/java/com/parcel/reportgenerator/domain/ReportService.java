package com.parcel.reportgenerator.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReportService {
    private final ReportGenerator reportGenerator;
    private final ParcelProvider parcelProvider;
    private final ReportRepository reportRepository;
    private final ContentHashProvider hashProvider;

    public Optional<ReportId> createReport(ReportRequest request) {
        var parcels = parcelProvider.getParcels(request);
        var hash = hashProvider.getContentHash(parcels);
        return reportRepository
                .findIdByContentHash(hash)
                .or(() -> createNewReport(request, parcels));
    }

    private Optional<ReportId> createNewReport(ReportRequest request, List<Parcel> parcels) {
        return reportGenerator.generateReport(request, parcels)
                .stream().peek(reportRepository::save)
                .map(ReportDocument::id)
                .findFirst();
    }
}
