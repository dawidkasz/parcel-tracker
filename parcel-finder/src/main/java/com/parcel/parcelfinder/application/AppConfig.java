package com.parcel.parcelfinder.application;

import com.parcel.parcelfinder.application.eventbus.AppEventBus;
import com.parcel.parcelfinder.application.eventbus.EventBus;
import com.parcel.parcelfinder.domain.events.ParcelStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final ParcelStatusChangedEventHandler parcelStatusChangedEventHandler;

    @Bean
    public EventBus eventBus() {
        return new AppEventBus.Builder()
            .subscribe(parcelStatusChangedEventHandler, ParcelStatusChangedEvent.class)
            .build();
    }
}
