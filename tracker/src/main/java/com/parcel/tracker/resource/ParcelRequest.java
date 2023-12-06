package com.parcel.tracker.resource;

public class ParcelRequest {

    private String name;

    private String description;

    public ParcelRequest() {
    }

    public ParcelRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
