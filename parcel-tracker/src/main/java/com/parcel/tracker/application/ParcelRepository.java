package com.parcel.tracker.application;

import com.parcel.tracker.domain.Parcel;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParcelRepository extends MongoRepository<Parcel, String> {
}
