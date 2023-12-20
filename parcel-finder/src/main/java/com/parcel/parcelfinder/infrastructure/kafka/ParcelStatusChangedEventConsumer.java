package com.parcel.parcelfinder.infrastructure.kafka;

import com.parcel.parcelfinder.application.eventbus.EventBus;
import com.parcel.parcelfinder.domain.events.ParcelStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ParcelStatusChangedEventConsumer {
    private final EventBus eventBus;

    @KafkaListener(topics = "${spring.kafka.parcel-status-change-topic}")
    public void listen(final ParcelStatusChangedEvent event) {
        eventBus.notify(event);
    }
}
