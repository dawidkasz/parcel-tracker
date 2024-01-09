package com.parcel.tracker.web;

import com.parcel.tracker.TrackerService;
import com.parcel.tracker.domain.ParcelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracking")
public class TrackingController {

    private final TrackerService trackerService;

    @Autowired
    public TrackingController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add-parcel")
    public void addParcelToTracking(@RequestBody ParcelRequest parcelRequest) {
        trackerService.addParcel(parcelRequest);
    }
}
