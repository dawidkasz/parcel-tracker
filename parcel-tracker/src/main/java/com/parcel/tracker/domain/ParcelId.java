package com.parcel.tracker.domain;

public record ParcelId(String id) {
    public static ParcelId of(String id) {
        return new ParcelId(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
