package com.parcel.reportgenerator.domain;

import java.util.List;

public record Parcel(
        String id, String carrier, List<ParcelStatus> history
) {
}
