package com.parcel.reportgenerator.infrastructure.persistence.redis;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface RedisReportRepository extends PagingAndSortingRepository<RedisReport, UUID>, CrudRepository<RedisReport, UUID> {
    Optional<RedisReport> findByContentHash(long hash);
}
