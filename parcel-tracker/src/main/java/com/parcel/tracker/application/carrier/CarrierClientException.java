package com.parcel.tracker.application.carrier;

import com.parcel.tracker.domain.Parcel;

public class CarrierClientException extends Exception {
    private CarrierClientException(String message) {
        super(message);
    }

    public static CarrierClientException cantCheckParcelStatus(Parcel parcel) {
        return new CarrierClientException(String.format(
            "Can't check parcel %s id for %s",
            parcel.getCarrier(),
            parcel.getId()
        ));
    }
}
