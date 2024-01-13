package com.parcel.tracker.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Value
@AllArgsConstructor
public class Parcel {
    ParcelId id;
    Carrier carrier;
    List<ParcelStatus> statuses;

    public Parcel(ParcelId id, Carrier carrier) {
        this(id, carrier, new ArrayList<>());
    }

    public Optional<ParcelStatus> latestStatus() {
        return statuses.isEmpty() ? Optional.empty() : Optional.of(statuses.get(statuses.size() - 1));
    }

    public void addNewStatus(ParcelStatus parcelStatus) {
        checkArgument(
            latestStatus().isEmpty() || !latestStatus().get().getStatus().equals(parcelStatus.getStatus()),
            String.format("Parcel %s status hasn't changed.", id)
        );

        statuses.add(parcelStatus);
    }
}
