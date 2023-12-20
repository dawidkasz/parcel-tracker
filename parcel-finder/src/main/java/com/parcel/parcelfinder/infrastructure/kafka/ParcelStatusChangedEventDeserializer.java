package com.parcel.parcelfinder.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.parcelfinder.domain.events.ParcelStatusChangedEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class ParcelStatusChangedEventDeserializer implements Deserializer<ParcelStatusChangedEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ParcelStatusChangedEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }
            return objectMapper.readValue(data, ParcelStatusChangedEvent.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to ParcelStatusChange " + e.getMessage());
        }
    }
}