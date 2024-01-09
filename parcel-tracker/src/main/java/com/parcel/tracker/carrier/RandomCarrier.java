package com.parcel.tracker.carrier;


import com.parcel.tracker.Tracker;
import com.parcel.tracker.model.Parcel;
import com.parcel.tracker.repository.ParcelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
public class RandomCarrier implements Carrier {

    private final ParcelRepository parcelRepository;

    @Autowired
    public RandomCarrier(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public void startTracking(Tracker tracker) {
        try {
            Parcel newParcel = new Parcel(tracker.getParcelId(), tracker.getParcel().getCarrierName(), new ArrayList<>());
            parcelRepository.save(newParcel);

            System.out.println("Parcel " + tracker.getParcelId() + "  tracking started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkParcelStatus(Tracker tracker) {
        try {
            if (shouldSetPendingStatus()) {
                tracker.updateStatus("pending");
            } else {
                String randomStatus = getRandomStatus();
                tracker.updateStatus(randomStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean shouldSetPendingStatus() {
        // 80% szans że status się nie zmieni
        int randomNumber = new Random().nextInt(100);
        return randomNumber < 80;
    }

    private String getRandomStatus() {
        // Wybieranie losowego statusu spośród innych
        String[] otherStatuses = {"shipped", "delivered", "in_transit", "delayed"};
        int randomIndex = new Random().nextInt(otherStatuses.length);
        return otherStatuses[randomIndex];
    }
}