package com.parcel.parcelfinder.application;

import com.parcel.parcelfinder.domain.Parcel;
import com.parcel.parcelfinder.domain.ParcelId;

import java.util.List;
import java.util.Optional;

public interface ParcelRepository {
    void save(Parcel parcel);

    List<Parcel> search(String query);

    Optional<Parcel> findById(ParcelId id);

    List<Parcel> findAll(int pageId);
}
