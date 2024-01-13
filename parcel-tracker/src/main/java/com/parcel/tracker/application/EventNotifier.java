package com.parcel.tracker.application;

import com.parcel.tracker.domain.events.AppEvent;
import org.springframework.stereotype.Component;

@Component
public interface EventNotifier<T extends AppEvent> {
    void notify(T event);
}
