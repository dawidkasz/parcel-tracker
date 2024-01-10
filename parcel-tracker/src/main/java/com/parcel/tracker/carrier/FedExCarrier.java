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
public class FedExCarrier implements Carrier {

    private final ParcelRepository parcelRepository;

    @Override
    public void startTracking(Tracker tracker) {
        // TODO request do API FedEx żeby dodać nową paczkę do śledzenia
        // TODO zapisywanie do mongodb nowej paczki z pustą listą statusów
        log.info("FedEx tracking started for parcel: {}", tracker.getParcelId());
    }

    @Override
    public void checkParcelStatus(Tracker tracker) {
        // TODO użyć API FedEx do sprawdzania statusu paczki, teraz zhardcodowane "Delivered"
        log.info("Checking parcel {} status for FedEx", tracker.getParcelId());
        tracker.updateStatus("Delivered");
    }
}