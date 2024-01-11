package com.parcel.tracker.application;

import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.domain.ParcelId;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository {
    Iterable<Parcel> findAll();
    boolean existsById(ParcelId parcelId);
    void save(Parcel parcel);
}
