package com.parcel.tracker;

import com.parcel.tracker.carrier.*;
import com.parcel.tracker.model.Parcel;
import com.parcel.tracker.model.ParcelRequest;
import com.parcel.tracker.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class TrackerService {

    private final List<Tracker> trackers;

    private final ParcelRepository parcelRepository;

    @Autowired
    public TrackerService(List<Tracker> trackers, ParcelRepository parcelRepository) {
        this.trackers = trackers;
        this.parcelRepository = parcelRepository;
    }

    public void addParcel(ParcelRequest parcelRequest) {
        Carrier carrier = findCarrierForParcel(parcelRequest);

        Tracker tracker = new Tracker(parcelRequest.getId(), convertParcelRequestToParcel(parcelRequest), carrier, parcelRepository);
        addTracker(tracker);
        tracker.startTracking();
    }

    private Carrier findCarrierForParcel(ParcelRequest parcelRequest) {
        String carrierName = parcelRequest.getCarrierName();

        if ("inpost".equalsIgnoreCase(carrierName)) {
            return new InPostCarrier(parcelRepository);
        } else if ("fedex".equalsIgnoreCase(carrierName)) {
            return new FedExCarrier(parcelRepository);
        } else if ("ship24".equalsIgnoreCase(carrierName)) {
            return new Ship24Carrier(parcelRepository);
        } else {
            return new RandomCarrier(parcelRepository);
        }
    }

    private Parcel convertParcelRequestToParcel(ParcelRequest parcelRequest) {
        return new Parcel(parcelRequest.getId(), parcelRequest.getCarrierName());
    }

    public void addTracker(Tracker tracker) {
        trackers.add(tracker);
    }

    @Scheduled(fixedDelay = 5000)
    public void checkAllTrackers() {
        trackers.forEach(Tracker::checkParcelStatus);
    }
}
