package com.parcel.tracker.application.carrier;

import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.domain.ParcelStatus;
import org.springframework.stereotype.Component;

@Component
public interface CarrierClient {
    ParcelStatus checkParcelStatus(Parcel parcel) throws CarrierClientException;
    Carrier getCarrier();
}