package com.parcel.parcelfinder.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SpringDataElasticParcelRepository extends ElasticsearchRepository<ElasticParcel, String> {
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"description\", \"status\"], \"fuzziness\": \"AUTO\"}}")
    List<ElasticParcel> find(String query);
}
