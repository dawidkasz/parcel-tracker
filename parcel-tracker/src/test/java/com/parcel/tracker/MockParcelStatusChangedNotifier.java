package com.parcel.tracker;

import com.parcel.tracker.application.EventNotifier;
import com.parcel.tracker.domain.events.ParcelStatusChangedEvent;

public class MockParcelStatusChangedNotifier implements EventNotifier<ParcelStatusChangedEvent> {
    @Override
    public void notify(ParcelStatusChangedEvent event) {
    }
}
