package com.parcel.parcelfinder.application;

import com.parcel.parcelfinder.application.eventbus.EventHandler;
import com.parcel.parcelfinder.domain.Parcel;
import com.parcel.parcelfinder.domain.events.ParcelStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcelStatusChangedEventHandler implements EventHandler<ParcelStatusChangedEvent> {
    private final ParcelRepository parcelRepository;

    public void handle(ParcelStatusChangedEvent event) {
        log.info("Handling {} event", event);

        Optional<Parcel> currentParcel = parcelRepository.findById(event.id());

        if (currentParcel.isPresent()) {
            log.debug("Parcel doesn't exist yet, creating...");
            var parcel = currentParcel.get();
            parcel.addNewStatus(event.status());
            parcelRepository.save(parcel);
        } else {
            log.debug("Parcel already exists. Appending new status");
            var parcel = new Parcel(event.id(), event.carrier(), List.of(event.status()));
            parcelRepository.save(parcel);
        }
    }
}
