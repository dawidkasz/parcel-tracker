package com.parcel.parcelfinder.domain;

import java.util.ArrayList;
import java.util.List;

public record Parcel(ParcelId parcelId, Carrier carrier, List<ParcelStatus> statusHistory) {
    public Parcel(ParcelId parcelId, Carrier carrier, List<ParcelStatus> statusHistory) {
        this.parcelId = parcelId;
        this.carrier = carrier;
        this.statusHistory = new ArrayList<>(statusHistory);
    }

    public void addNewStatus(ParcelStatus status) {
        statusHistory.add(status);
    }
}
