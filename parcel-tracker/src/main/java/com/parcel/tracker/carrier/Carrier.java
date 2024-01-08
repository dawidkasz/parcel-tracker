package com.parcel.tracker.carrier;

import com.parcel.tracker.Tracker;

public interface Carrier {
    void startTracking(Tracker tracker);
    void checkParcelStatus(Tracker tracker);
}