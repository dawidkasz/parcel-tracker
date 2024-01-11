package com.parcel.tracker.infrastructure.kafka;

import com.parcel.tracker.application.EventNotifier;
import com.parcel.tracker.domain.ParcelStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaParcelStatusChangedNotifier implements EventNotifier<ParcelStatusChangedEvent> {
    @Value("${spring.kafka.parcel-status-change-topic}")
    private String topic;

    private final KafkaProducer<String, ParcelStatusChangedEvent> kafkaProducer;

    @Override
    public void notify(ParcelStatusChangedEvent event) {
        ProducerRecord<String, ParcelStatusChangedEvent> producerRecord = new ProducerRecord<>(topic, event);

        kafkaProducer.send(producerRecord);
        kafkaProducer.flush();
    }
}
