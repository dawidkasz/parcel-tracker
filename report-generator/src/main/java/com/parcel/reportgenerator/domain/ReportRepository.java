package com.parcel.reportgenerator.domain;

import java.util.Optional;

public interface ReportRepository {
    void save(ReportDocument document);

    Optional<ReportId> findIdByContentHash(long contentHash);

    Optional<ReportDocument> findById(ReportId reportId);
}
