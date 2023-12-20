package com.parcel.parcelfinder.domain;

import java.time.Instant;

public record Parcel(String id, String description, String status, Instant updatedAt) {
}
