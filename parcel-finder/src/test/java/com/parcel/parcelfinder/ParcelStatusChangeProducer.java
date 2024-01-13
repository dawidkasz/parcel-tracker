package com.parcel.parcelfinder;

import com.parcel.parcelfinder.domain.Carrier;
import com.parcel.parcelfinder.domain.ParcelId;
import com.parcel.parcelfinder.domain.ParcelStatus;
import com.parcel.parcelfinder.domain.events.ParcelStatusChangedEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Instant;
import java.util.Properties;

public class ParcelStatusChangeProducer {
    public static void main(String[] args) {

        String bootstrapServers = "127.0.0.1:29092";

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<String, ParcelStatusChangedEvent> producer = new KafkaProducer<>(properties);

        var event = new ParcelStatusChangedEvent(ParcelId.of("a"), Carrier.IN_MEMORY, new ParcelStatus("a", Instant.now(), "b"));
        ProducerRecord<String, ParcelStatusChangedEvent> producerRecord = new ProducerRecord<>("parcel-status-change", event);

        producer.send(producerRecord);

        producer.flush();
        producer.close();
    }
}
