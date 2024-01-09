package com.parcel.tracker.domain;

public record ParcelStatusChangedEvent(String id, String status, String description) {
}
