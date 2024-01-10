package com.parcel.tracker;

import com.parcel.tracker.carrier.*;
import com.parcel.tracker.domain.ParcelRequest;
import com.parcel.tracker.eventbus.EventBus;
import com.parcel.tracker.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackerService {

    private final List<Tracker> trackers;

    private final ParcelRepository parcelRepository;
    private final EventBus eventBus;

    public void addParcel(ParcelRequest parcelRequest) {
        Carrier carrier = findCarrierForParcel(parcelRequest);

        Tracker tracker = new Tracker(parcelRequest.getId(), parcelRequest.toParcel(), carrier, parcelRepository, eventBus);
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

    public void addTracker(Tracker tracker) {
        trackers.add(tracker);
    }

    @Scheduled(fixedDelay = 5000)
    public void checkAllTrackers() {
        trackers.forEach(Tracker::checkParcelStatus);
    }
}
