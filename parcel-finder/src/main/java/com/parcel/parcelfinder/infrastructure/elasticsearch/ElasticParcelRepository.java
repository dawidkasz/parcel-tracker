package com.parcel.parcelfinder.infrastructure.elasticsearch;

import com.parcel.parcelfinder.application.ParcelRepository;
import com.parcel.parcelfinder.domain.Parcel;
import com.parcel.parcelfinder.domain.ParcelId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ElasticParcelRepository implements ParcelRepository {
    private final SpringDataElasticParcelRepository elasticParcelRepository;

    @Override
    public void save(Parcel parcel) {
        log.info("Saving parcel {} to elasticsearch", parcel);
        elasticParcelRepository.save(ElasticParcel.from(parcel));
    }

    @Override
    public List<Parcel> search(String query) {
        log.info("Searching for parcels using query {}", query);

        return elasticParcelRepository.find(query).stream()
            .map(ElasticParcel::toParcel)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Parcel> findById(ParcelId id) {
        return elasticParcelRepository.findById(id.id()).map(ElasticParcel::toParcel);
    }

    @Override
    public List<Parcel> findAll(int pageId) {
        return elasticParcelRepository.findAll(PageRequest.of(pageId, 20)).stream()
                .map(ElasticParcel::toParcel)
                .toList();
    }
}


