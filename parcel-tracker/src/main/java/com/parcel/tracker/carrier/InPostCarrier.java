package com.parcel.tracker.carrier;

import com.parcel.tracker.Tracker;
import com.parcel.tracker.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InPostCarrier implements Carrier {

    private final ParcelRepository parcelRepository;

    @Autowired
    public InPostCarrier(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public void startTracking(Tracker tracker) {
        // TODO request do API InPost żeby dodać nową paczkę do śledzenia
        // TODO zapisywanie do mongodb nowej paczki z pustą listą statusów
        System.out.println("InPost tracking started for parcel: " + tracker.getParcelId());
    }

    @Override
    public void checkParcelStatus(Tracker tracker) {
        // TODO użyć API InPost do sprawdzania statusu paczki, teraz zhardcodowane "Delivered"
        System.out.println("Checking parcel status for InPost");
        tracker.updateStatus("Delivered");
    }
}