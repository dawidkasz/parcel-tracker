package com.parcel.tracker;

import com.parcel.tracker.application.Tracker;
import com.parcel.tracker.application.carrier.CarrierClient;
import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.DomainException;
import com.parcel.tracker.domain.ParcelId;
import com.parcel.tracker.domain.ParcelStatus;
import com.parcel.tracker.domain.events.ParcelStatusChangedEvent;
import com.parcel.tracker.infrastructure.carrierclient.InMemoryCarrierClient;
import com.parcel.tracker.infrastructure.web.TrackingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TrackerTest {
    private TrackingController trackingController;
    private Tracker tracker;
    private CarrierClient carrierClient;
    private InMemoryParcelRepository parcelRepository;
    private MockParcelStatusChangedNotifier notifier;

    @BeforeEach
    public void init() {
        carrierClient = new InMemoryCarrierClient();
        parcelRepository = new InMemoryParcelRepository();
        notifier = mock(MockParcelStatusChangedNotifier.class);

        tracker = new Tracker(
            List.of(carrierClient),
            parcelRepository,
            List.of(notifier)
        );

        trackingController = new TrackingController(tracker);
    }

    @Test
    public void should_start_tracking_parcel() throws DomainException {
        // given:
        var parcelId = ParcelId.of("abc");
        var request = new TrackingController.StartParcelTrackingRequest(parcelId.id(), Carrier.IN_MEMORY);

        // when:
        trackingController.addParcelToTracking(request);

        // then:
        await().atMost(2, TimeUnit.SECONDS).until(
                () -> parcelRepository.getParcelStatus(parcelId).getStatus().equals("received"));

        var expectedEvent = new ParcelStatusChangedEvent(parcelId, Carrier.IN_MEMORY, ParcelStatus.of("received", null, null));
        verify(notifier, times(1)).notify(any());
    }

    @Test
    public void should_change_parcel_status() throws DomainException {
        // given:
        var parcelId = ParcelId.of("abc");
        var request = new TrackingController.StartParcelTrackingRequest(parcelId.id(), Carrier.IN_MEMORY);

        // when:
        trackingController.addParcelToTracking(request);
        tracker.checkAllTrackers();

        // then:
        await().atMost(2, TimeUnit.SECONDS).until(
                () -> parcelRepository.getParcelStatus(parcelId).getStatus().equals("preparing"));

        var expectedEvent = new ParcelStatusChangedEvent(parcelId, Carrier.IN_MEMORY, ParcelStatus.of("preparing", null, null));
        verify(notifier).notify(argThat(new ParcelStatusChangedEventMatcher(expectedEvent)));
    }

    @Test
    public void should_throw_an_error_when_duplicated_parcel_id() throws DomainException {
        // given:
        var request1 = new TrackingController.StartParcelTrackingRequest("123", Carrier.IN_MEMORY);
        var request2 = new TrackingController.StartParcelTrackingRequest("123", Carrier.IN_MEMORY);

        // when:
        trackingController.addParcelToTracking(request1);

        // then:
        Throwable exc = assertThrows(Tracker.ParcelAlreadyTrackedException.class, () -> trackingController.addParcelToTracking(request2));
        assertEquals("Parcel 123 is already tracked", exc.getMessage());
    }

    public static class ParcelStatusChangedEventMatcher implements ArgumentMatcher<ParcelStatusChangedEvent> {
        private final ParcelStatusChangedEvent left;

        public ParcelStatusChangedEventMatcher(ParcelStatusChangedEvent event) {
            this.left = event;
        }

        @Override
        public boolean matches(ParcelStatusChangedEvent right) {
            return left.id().equals(right.id()) &&
                    left.carrier().equals(right.carrier()) &&
                    left.status().getStatus().equals(right.status().getStatus());
        }
    }
}
