package com.parcel.tracker.carrier;

import com.parcel.tracker.Tracker;
import com.parcel.tracker.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FedExCarrier implements Carrier {

    private final ParcelRepository parcelRepository;

    @Autowired
    public FedExCarrier(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }
    @Override
    public void startTracking(Tracker tracker) {
        // TODO request do API FedEx żeby dodać nową paczkę do śledzenia
        // TODO zapisywanie do mongodb nowej paczki z pustą listą statusów
        System.out.println("FedEx tracking started for parcel: " + tracker.getParcelId());
    }

    @Override
    public void checkParcelStatus(Tracker tracker) {
        // TODO użyć API FedEx do sprawdzania statusu paczki, teraz zhardcodowane "Delivered"
        System.out.println("Checking parcel status for FedEx");
        tracker.updateStatus("Delivered");
    }
}