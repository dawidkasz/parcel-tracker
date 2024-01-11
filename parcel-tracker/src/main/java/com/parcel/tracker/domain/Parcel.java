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
    String id;
    Carrier carrier;
    String description;
    List<ParcelStatus> statuses;

    public Parcel(String id, Carrier carrier, String description) {
        this(id, carrier, description, new ArrayList<>());
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
