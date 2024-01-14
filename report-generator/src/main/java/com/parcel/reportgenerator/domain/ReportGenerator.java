package com.parcel.reportgenerator.domain;

import java.util.List;
import java.util.Optional;

public interface ReportGenerator {
    Optional<ReportDocument> generateReport(ReportRequest request, List<Parcel> parcels);
}
