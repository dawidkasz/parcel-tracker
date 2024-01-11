package com.parcel.tracker.application.carrier;

import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.Parcel;

public interface CarrierClient {
    String checkParcelStatus(Parcel parcel) throws CarrierClientException;
    Carrier getCarrier();
}