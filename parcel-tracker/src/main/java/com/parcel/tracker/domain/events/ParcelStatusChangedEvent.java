package com.parcel.tracker.domain.events;

import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.ParcelId;
import com.parcel.tracker.domain.ParcelStatus;

public record ParcelStatusChangedEvent(ParcelId id, Carrier carrier, ParcelStatus status) implements AppEvent {
}
