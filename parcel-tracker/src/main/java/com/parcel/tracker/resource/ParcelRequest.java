package com.parcel.tracker.resource;

import com.parcel.tracker.model.Status;

import java.util.List;

public class ParcelRequest {

    private String name;

    private List<Status> statuses;

    public ParcelRequest() {
    }

    public ParcelRequest(String name, List<Status> statuses) {
        this.name = name;
        this.statuses = statuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
