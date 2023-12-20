package com.parcel.parcelfinder.infrastructure.elasticsearch;

import com.parcel.parcelfinder.application.ParcelRepository;
import com.parcel.parcelfinder.domain.Parcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<Parcel> findAll() {
       return StreamSupport.stream(elasticParcelRepository.findAll().spliterator(), false)
               .map(ElasticParcel::toParcel)
               .collect(Collectors.toList());
    }
}


