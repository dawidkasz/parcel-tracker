package com.parcel.tracker;

import com.parcel.tracker.model.ParcelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tracking")
public class TrackingController {

    private final TrackerService trackerService;

    @Autowired
    public TrackingController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @PostMapping("/add-parcel")
    public void addParcelToTracking(@RequestBody ParcelRequest parcelRequest) {
        trackerService.addParcel(parcelRequest);
    }
}
