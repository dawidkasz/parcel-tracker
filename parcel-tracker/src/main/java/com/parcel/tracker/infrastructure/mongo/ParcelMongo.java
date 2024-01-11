package com.parcel.tracker.infrastructure.mongo;

import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.domain.ParcelId;
import com.parcel.tracker.domain.ParcelStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public record ParcelMongo(
        @Id String id,
        Carrier carrier,
        String description,
        List<ParcelStatus> statuses
) {
    public static ParcelMongo from(Parcel parcel) {
        return new ParcelMongo(
                parcel.getId().id(),
                parcel.getCarrier(),
                parcel.getDescription(),
                parcel.getStatuses()
        );
    }

    public Parcel toParcel() {
        return new Parcel(ParcelId.of(id), carrier, description, statuses);
    }
}
