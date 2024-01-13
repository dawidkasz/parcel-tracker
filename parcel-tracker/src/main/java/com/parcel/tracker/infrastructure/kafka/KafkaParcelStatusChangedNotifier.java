package com.parcel.tracker.infrastructure.kafka;

import com.parcel.tracker.application.EventNotifier;
import com.parcel.tracker.domain.ParcelStatus;
import com.parcel.tracker.domain.events.ParcelStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaParcelStatusChangedNotifier implements EventNotifier<ParcelStatusChangedEvent> {
    @Value("${spring.kafka.parcel-status-change-topic}")
    private String topic;

    private final KafkaTemplate<String, ParcelStatusChangedEvent> kafkaTemplate;

    @Override
    public void notify(ParcelStatusChangedEvent event) {
        log.debug("Sending event {} to kafka topic {}", event, topic);
        kafkaTemplate.send(topic, event);
    }
}
