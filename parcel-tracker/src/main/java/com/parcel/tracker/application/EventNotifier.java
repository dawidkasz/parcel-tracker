package com.parcel.tracker.application;

import com.parcel.tracker.domain.AppEvent;
import org.springframework.stereotype.Component;

@Component
public interface EventNotifier<T extends AppEvent> {
    void notify(T event);
}
