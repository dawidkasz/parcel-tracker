package com.parcel.tracker.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataParcelRepository extends MongoRepository<ParcelMongo, String> {
}
