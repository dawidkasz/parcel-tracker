package com.parcel.tracker.domain;

public record ParcelStatusChangedEvent(ParcelId id, String status, String description) implements AppEvent {
}
