package com.parcel.reportgenerator.domain;

import java.util.List;

public record Parcel(
        long id,
        String destination,
        String origin,
        String weight,
        List<Status> status
) {
}
