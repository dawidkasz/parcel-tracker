package com.parcel.tracker.infrastructure.carrierclient;

import com.parcel.tracker.application.carrier.CarrierClient;
import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.Parcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InPostCarrierClient implements CarrierClient {
    @Override
    public String checkParcelStatus(Parcel parcel) {
        // TODO użyć API InPost do sprawdzania statusu paczki, teraz zhardcodowane "Delivered"
        log.info("Checking parcel {} status for InPost", parcel.getId());
        return null;
    }

    @Override
    public Carrier getCarrier() {
        return Carrier.INPOST;
    }
}