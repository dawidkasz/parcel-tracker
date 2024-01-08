package com.parcel.tracker;

import com.parcel.tracker.carrier.Carrier;
import com.parcel.tracker.model.Parcel;
import com.parcel.tracker.model.ParcelStatus;
import com.parcel.tracker.repository.ParcelRepository;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class Tracker {

    private final String parcelId;
    private final Parcel parcel;
    private final Carrier carrier;
    private final List<ParcelStatus> statusHistory;
    private final ParcelRepository parcelRepository;

    public Tracker(String parcelId, Parcel parcel, Carrier carrier, ParcelRepository parcelRepository) {
        this.parcelId = parcelId;
        this.parcel = parcel;
        this.carrier = carrier;
        this.statusHistory = new ArrayList<>();
        this.parcelRepository = parcelRepository;
    }

    public void startTracking() {
        carrier.startTracking(this);
    }

    public void checkParcelStatus() {
        carrier.checkParcelStatus(this);
    }

    public void updateStatus(String status) {
        if (statusHistory.stream().noneMatch(parcelStatus -> parcelStatus.getStatus().equals(status))) {
            ParcelStatus parcelStatus = new ParcelStatus(status, Instant.now());
            statusHistory.add(parcelStatus);
            System.out.println("Parcel " + parcelId + " status updated: " + status);
            System.out.println(statusHistory);

            // Dodanie nowego statusu do bazy danych
            parcelRepository.findById(parcelId).ifPresent(parcel -> {
                parcel.getStatuses().add(parcelStatus);
                parcelRepository.save(parcel);
            });
        }
    }
}
