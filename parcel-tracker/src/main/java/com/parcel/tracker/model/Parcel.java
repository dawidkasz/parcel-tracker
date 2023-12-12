package com.parcel.tracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("parcels")
public class Parcel {

    @Id
    private String id;

    private String name;

    private List<Status> statuses;

    public Parcel() {
    }

    public Parcel(String name, List<Status> statuses) {
        this.name = name;
        this.statuses = statuses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
