package com.parcel.tracker.model;

public class Status {

    private String status;
    private long timestamp;

    public Status() {
    }

    public Status(String status, long timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
