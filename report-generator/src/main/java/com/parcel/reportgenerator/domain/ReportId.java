package com.parcel.reportgenerator.domain;

import java.util.UUID;

public record ReportId(UUID uuid) {
    public static ReportId nextIdentity() {
        return new ReportId(UUID.randomUUID());
    }
}
