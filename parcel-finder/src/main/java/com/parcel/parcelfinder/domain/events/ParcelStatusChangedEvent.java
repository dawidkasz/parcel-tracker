package com.parcel.parcelfinder.domain.events;

import com.parcel.parcelfinder.domain.Carrier;
import com.parcel.parcelfinder.domain.ParcelId;
import com.parcel.parcelfinder.domain.ParcelStatus;

public record ParcelStatusChangedEvent(ParcelId id, Carrier carrier, ParcelStatus status) implements AppEvent {
}

