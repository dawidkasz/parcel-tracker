package com.parcel.parcelfinder.domain.events;

public record ParcelStatusChangedEvent(String id, String status) implements AppEvent {
}
