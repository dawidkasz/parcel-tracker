package com.parcel.tracker.application.carrier;

import com.parcel.tracker.domain.Parcel;

public class CarrierClientException extends Exception {
    public CarrierClientException(String message) {
        super(message);
    }

    public CarrierClientException(Exception exc) {
        super(exc);
    }

    public static CarrierClientException cantCheckParcelStatus(Parcel parcel) {
        return new CarrierClientException(String.format(
            "Can't check parcel %s id for %s",
            parcel.getCarrier(),
            parcel.getId()
        ));
    }
}
