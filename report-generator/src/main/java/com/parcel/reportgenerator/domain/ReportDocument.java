package com.parcel.reportgenerator.domain;

import java.time.Instant;

public record ReportDocument(ReportId id, String description, byte[] document, long contentHash, Instant created) {
}
