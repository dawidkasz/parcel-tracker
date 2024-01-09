package com.parcel.tracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Parcel {
    private String id;
    private String carrierName;
    private String description;
    private List<ParcelStatus> statuses;

    public Parcel() {
    }

    public Parcel(String id, String carrierName, String description) {
        this.id = id;
        this.carrierName = carrierName;
        this.statuses = new ArrayList<>();
    }
}
