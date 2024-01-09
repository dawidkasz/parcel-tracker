package com.parcel.tracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParcelRequest {
    private String id;
    private String carrierName;
    private String description;

    public Parcel toParcel() {
        return new Parcel(id, carrierName, description);
    }
}
