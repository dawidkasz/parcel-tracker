package com.parcel.tracker.infrastructure.carrierclient;


import com.parcel.tracker.application.carrier.CarrierClient;
import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.Parcel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class RandomCarrierClient implements CarrierClient {
    @Override
    public String checkParcelStatus(Parcel parcel) {
        return shouldSetPendingStatus() ? "pending" : getRandomStatus();
    }

    @Override
    public Carrier getCarrier() {
        return Carrier.RANDOM;
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