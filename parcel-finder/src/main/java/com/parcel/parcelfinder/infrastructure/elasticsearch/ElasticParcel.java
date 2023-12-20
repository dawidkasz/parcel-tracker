package com.parcel.parcelfinder.infrastructure.elasticsearch;

import com.parcel.parcelfinder.domain.Parcel;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@ToString
@Document(indexName="parcels")
public class ElasticParcel {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String status;

    public ElasticParcel() {
    }

    public ElasticParcel(String id, String description, String status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public static ElasticParcel from(Parcel parcel) {
        return new ElasticParcel(
            parcel.id(),
            parcel.description(),
            parcel.status()
        );
    }

    public Parcel toParcel() {
        return new Parcel(id, description, status, Instant.now());
    }
}