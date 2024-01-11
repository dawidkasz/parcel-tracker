package com.parcel.tracker.infrastructure.kafka;

import com.parcel.tracker.application.EventNotifier;
import com.parcel.tracker.domain.ParcelStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaParcelStatusChangedNotifier implements EventNotifier<ParcelStatusChangedEvent> {
    @Value("${spring.kafka.parcel-status-change-topic}")
    private String topic;

    private final KafkaTemplate<String, ParcelStatusChangedEvent> kafkaTemplate;

    @Override
    public void notify(ParcelStatusChangedEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
