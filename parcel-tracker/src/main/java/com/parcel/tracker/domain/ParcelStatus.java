package com.parcel.tracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ParcelStatus {
    private String status;
    private Instant timestamp;
}
