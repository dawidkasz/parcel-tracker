package com.parcel.tracker.carrier;

import com.parcel.tracker.Tracker;
import com.parcel.tracker.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InPostCarrier implements Carrier {

    private final ParcelRepository parcelRepository;

    @Override
    public void startTracking(Tracker tracker) {
        // TODO request do API InPost żeby dodać nową paczkę do śledzenia
        // TODO zapisywanie do mongodb nowej paczki z pustą listą statusów
        log.info("FedEx tracking started for parcel: {}", tracker.getParcelId());
    }

    @Override
    public void checkParcelStatus(Tracker tracker) {
        // TODO użyć API InPost do sprawdzania statusu paczki, teraz zhardcodowane "Delivered"
        log.info("Checking parcel {} status for InPost", tracker.getParcelId());
        tracker.updateStatus("Delivered");
    }
}