package com.parcel.tracker.application;

import org.springframework.stereotype.Component;

@Component
public interface EventNotifier<T> {
    void notify(T event);
}
