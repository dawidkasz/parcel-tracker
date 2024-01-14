package com.parcel.parcelfinder;

import com.parcel.parcelfinder.application.AppConfig;
import com.parcel.parcelfinder.application.ParcelRepository;
import com.parcel.parcelfinder.application.ParcelStatusChangedEventHandler;
import com.parcel.parcelfinder.application.eventbus.EventBus;
import com.parcel.parcelfinder.domain.Carrier;
import com.parcel.parcelfinder.domain.Parcel;
import com.parcel.parcelfinder.domain.ParcelId;
import com.parcel.parcelfinder.domain.ParcelStatus;
import com.parcel.parcelfinder.domain.events.ParcelStatusChangedEvent;
import com.parcel.parcelfinder.infrastructure.kafka.ParcelStatusChangedEventConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class ParcelStatusChangedTest {
    ParcelRepository parcelRepository;
    ParcelStatusChangedEventHandler parcelStatusChangedEventHandler;
    EventBus eventBus;
    ParcelStatusChangedEventConsumer parcelStatusChangedEventConsumer;

    @BeforeEach
    void setup() {
        parcelRepository = mock(ParcelRepository.class);
        parcelStatusChangedEventHandler = new ParcelStatusChangedEventHandler(parcelRepository);
        eventBus = new AppConfig(parcelStatusChangedEventHandler).eventBus();
        parcelStatusChangedEventConsumer = new ParcelStatusChangedEventConsumer(eventBus);
    }

    @Test
    void should_save_new_parcel() {
        // given:
        var parcelId =  ParcelId.of("123");
        var parcelStatus = new ParcelStatus("some status", Instant.now(), "some desc");
        var event = new ParcelStatusChangedEvent(parcelId, Carrier.INPOST, parcelStatus);

        when(parcelRepository.findById(parcelId)).thenReturn(Optional.empty());

        // when:
        parcelStatusChangedEventConsumer.listen(event);

        // then:
        verify(parcelRepository).save(new Parcel(parcelId, Carrier.INPOST, List.of(parcelStatus)));
    }

    @Test
    void should_update_parcel_status_history() {
        // given:
        var existingStatus = new ParcelStatus("prev", Instant.now(), "abc");

        var parcelId =  ParcelId.of("123");
        var parcelStatus = new ParcelStatus("some status", Instant.now(), "some desc");
        var event = new ParcelStatusChangedEvent(parcelId, Carrier.INPOST, parcelStatus);

        when(parcelRepository.findById(parcelId)).thenReturn(Optional.of(new Parcel(
                parcelId,
                Carrier.INPOST,
                new ArrayList<>(List.of(existingStatus))
        )));

        // when:
        parcelStatusChangedEventConsumer.listen(event);

        // then:
        verify(parcelRepository).save(new Parcel(parcelId, Carrier.INPOST, List.of(existingStatus, parcelStatus)));
    }
}
