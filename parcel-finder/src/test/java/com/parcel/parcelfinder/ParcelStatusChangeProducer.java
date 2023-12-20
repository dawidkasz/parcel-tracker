package com.parcel.parcelfinder;

import com.parcel.parcelfinder.domain.events.ParcelStatusChangedEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Properties;

public class ParcelStatusChangeProducer {
    public static void main(String[] args) {

        String bootstrapServers = "127.0.0.1:29092";

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<String, ParcelStatusChangedEvent> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, ParcelStatusChangedEvent> producerRecord =
                new ProducerRecord<>("parcel-status-change", new ParcelStatusChangedEvent("a", "status3"));

        producer.send(producerRecord);

        producer.flush();
        producer.close();
    }
}
