package com.parcel.parcelfinder.application;

import com.parcel.parcelfinder.domain.Parcel;

import java.util.List;

public interface ParcelRepository {
    void save(Parcel parcel);

    List<Parcel> search(String query);
}
