package com.parcel.tracker.domain;

import lombok.Value;

import java.time.Instant;

@Value(staticConstructor = "of")
public class ParcelStatus {
    String status;
    Instant timestamp;
    String description;
}
