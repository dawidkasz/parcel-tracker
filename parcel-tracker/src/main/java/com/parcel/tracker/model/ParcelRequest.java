package com.parcel.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParcelRequest {
    private String id;
    private String carrierName;
}
