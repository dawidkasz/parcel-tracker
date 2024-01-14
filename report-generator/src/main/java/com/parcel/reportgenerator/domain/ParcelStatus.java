package com.parcel.reportgenerator.domain;

import java.time.Instant;

public record ParcelStatus(
        String status,
        Instant timestamp,
        String description
) {
}
