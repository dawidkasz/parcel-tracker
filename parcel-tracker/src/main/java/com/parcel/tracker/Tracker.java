package com.parcel.tracker;

import com.parcel.tracker.carrier.Carrier;
import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.domain.ParcelStatus;
import com.parcel.tracker.domain.ParcelStatusChangedEvent;
import com.parcel.tracker.eventbus.EventBus;
import com.parcel.tracker.repository.ParcelRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class Tracker {

    private final String parcelId;
    private final Parcel parcel;
    private final Carrier carrier;
    private final List<ParcelStatus> statusHistory;
    private final ParcelRepository parcelRepository;
    private final EventBus eventBus;

    public Tracker(String parcelId, Parcel parcel, Carrier carrier, ParcelRepository parcelRepository, EventBus eventBus) {
        this.parcelId = parcelId;
        this.parcel = parcel;
        this.carrier = carrier;
        this.statusHistory = new ArrayList<>();
        this.parcelRepository = parcelRepository;
        this.eventBus = eventBus;
    }

    public void startTracking() {
        carrier.startTracking(this);
        String status = parcel.getStatuses().isEmpty() ? "" : parcel.getStatuses().get(parcel.getStatuses().size() - 1).getStatus();
        eventBus.notify(new ParcelStatusChangedEvent(parcelId, status, parcel.getDescription()));
    }

    public void checkParcelStatus() {
        carrier.checkParcelStatus(this);
    }

    public void updateStatus(String status) {
        if (statusHistory.stream().noneMatch(parcelStatus -> parcelStatus.getStatus().equals(status))) {
            ParcelStatus parcelStatus = new ParcelStatus(status, Instant.now());
            statusHistory.add(parcelStatus);

            log.info("Parcel {} status has been updated to {}. Status history: {}", parcelId, status, statusHistory);

            // Dodanie nowego statusu do bazy danych
            parcelRepository.findById(parcelId).ifPresent(parcel -> {
                parcel.getStatuses().add(parcelStatus);
                parcelRepository.save(parcel);
            });

            eventBus.notify(new ParcelStatusChangedEvent(parcelId, status, parcel.getDescription()));
        }
    }
}
