package com.parcel.tracker.repository;

import com.parcel.tracker.model.Parcel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParcelRepository extends MongoRepository<Parcel, String> {
}
