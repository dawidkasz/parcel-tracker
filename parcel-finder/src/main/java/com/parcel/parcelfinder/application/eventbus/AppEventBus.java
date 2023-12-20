package com.parcel.parcelfinder.application.eventbus;

import com.parcel.parcelfinder.domain.events.AppEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AppEventBus implements EventBus {
    private final Map<Class<? extends AppEvent>, Set<EventHandler<? extends AppEvent>>> consumers;

    @Override
    @SuppressWarnings("unchecked")
    public <T extends AppEvent> void notify(T event) {
        log.info("Notifying about {} event", event);

        consumers.getOrDefault(event.getClass(), Collections.emptySet())
            .forEach(consumer -> ((EventHandler<T>) consumer).handle(event));
    }

    public static class Builder {
        private final Map<Class<? extends AppEvent>, Set<EventHandler<? extends AppEvent>>> consumers = new HashMap<>();

        public <T extends AppEvent> Builder subscribe(EventHandler<? extends AppEvent> consumer, Class<T> clazz) {
            consumers.putIfAbsent(clazz, new HashSet<>());
            consumers.get(clazz).add(consumer);
            return this;
        }

        public AppEventBus build() {
            return new AppEventBus(consumers);
        }
    }
}
