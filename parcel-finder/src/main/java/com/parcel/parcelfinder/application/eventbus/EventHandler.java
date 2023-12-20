package com.parcel.parcelfinder.application.eventbus;

public interface EventHandler<T> {
    void handle(T event);
}
