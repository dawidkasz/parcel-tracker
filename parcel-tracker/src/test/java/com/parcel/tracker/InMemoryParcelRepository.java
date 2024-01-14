package com.parcel.tracker;

import com.parcel.tracker.application.ParcelRepository;
import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.domain.ParcelId;
import com.parcel.tracker.domain.ParcelStatus;

import java.util.HashMap;
import java.util.Map;

public class InMemoryParcelRepository implements ParcelRepository {
    private final Map<ParcelId, Parcel> store = new HashMap<>();

    @Override
    public Iterable<Parcel> findAll() {
        return store.values();
    }

    @Override
    public boolean existsById(ParcelId parcelId) {
        return store.containsKey(parcelId);
    }

    @Override
    public void save(Parcel parcel) {
        store.put(parcel.getId(), parcel);
    }

    public ParcelStatus getParcelStatus(ParcelId parcelId) {
        return store.get(parcelId).latestStatus().orElseThrow(RuntimeException::new);
    }
}
