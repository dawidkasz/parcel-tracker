package com.parcel.parcelfinder.application.eventbus;

import com.parcel.parcelfinder.domain.events.AppEvent;

public interface EventBus {
    <T extends AppEvent> void notify(T event);
}
