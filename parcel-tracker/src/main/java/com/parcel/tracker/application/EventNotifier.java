package com.parcel.tracker.application;

public interface EventNotifier<T> {
    void notify(T event);
}
