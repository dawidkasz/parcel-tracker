package com.parcel.tracker.eventbus;

import com.parcel.tracker.domain.ParcelStatusChangedEvent;

public interface EventBus {
  void notify(ParcelStatusChangedEvent event);
}
