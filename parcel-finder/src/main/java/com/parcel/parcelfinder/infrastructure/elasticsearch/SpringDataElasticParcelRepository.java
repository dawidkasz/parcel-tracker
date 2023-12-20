package com.parcel.parcelfinder.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SpringDataElasticParcelRepository extends ElasticsearchRepository<ElasticParcel, String> {
}
