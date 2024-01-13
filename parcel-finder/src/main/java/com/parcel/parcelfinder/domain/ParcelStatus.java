package com.parcel.parcelfinder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelStatus {
    String status;
    Instant timestamp;
    String description;
}
