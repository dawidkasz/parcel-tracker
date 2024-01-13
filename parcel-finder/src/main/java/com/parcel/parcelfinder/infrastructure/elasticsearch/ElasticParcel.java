package com.parcel.parcelfinder.infrastructure.elasticsearch;

import com.parcel.parcelfinder.domain.Carrier;
import com.parcel.parcelfinder.domain.Parcel;
import com.parcel.parcelfinder.domain.ParcelId;
import com.parcel.parcelfinder.domain.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.List;

@ToString
@Document(indexName="parcels")
public class ElasticParcel {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String carrier;

    @Field(type = FieldType.Nested)
    private List<ElasticParcelStatus> statusHistory;

    public ElasticParcel() {
    }

    public ElasticParcel(String id, String carrier, List<ParcelStatus> statusHistory) {
        this.id = id;
        this.carrier = carrier;
        this.statusHistory = statusHistory.stream()
                .map(ElasticParcelStatus::from)
                .toList();
    }

    public static ElasticParcel from(Parcel parcel) {
        return new ElasticParcel(
            parcel.parcelId().id(),
            parcel.carrier().name(),
            parcel.statusHistory()
        );
    }

    public Parcel toParcel() {
        var mappedStatusHistory = statusHistory.stream()
                .map(ElasticParcelStatus::toParcelStatus)
                .toList();

        return new Parcel(ParcelId.of(id), Carrier.valueOf(carrier), mappedStatusHistory);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ElasticParcelStatus {
        @Field(type = FieldType.Text)
        private String status;

        @Field(type = FieldType.Integer)
        private long timestamp;

        @Field(type = FieldType.Text)
        private String description;


        public static ElasticParcelStatus from(ParcelStatus status) {
            return new ElasticParcelStatus(
                status.getStatus(),
                status.getTimestamp().toEpochMilli(),
                status.getDescription()
            );
        }

        public ParcelStatus toParcelStatus() {
            return new ParcelStatus(status, Instant.ofEpochMilli(timestamp), description);
        }
    }
}