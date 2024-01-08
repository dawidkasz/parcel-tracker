package com.parcel.parcelfinder.application;

import com.parcel.parcelfinder.application.eventbus.EventHandler;
import com.parcel.parcelfinder.domain.Parcel;
import com.parcel.parcelfinder.domain.events.ParcelStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcelStatusChangedEventHandler implements EventHandler<ParcelStatusChangedEvent> {
    private final ParcelRepository parcelRepository;

    public void handle(ParcelStatusChangedEvent event) {
        log.info("Handling {} event", event);

        Parcel parcel = new Parcel(
            event.id(),
            event.description(),
            event.status(),
            Instant.now()
        );

        parcelRepository.save(parcel);
    }
}
