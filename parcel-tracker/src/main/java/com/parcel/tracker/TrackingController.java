package com.parcel.tracker;

import com.parcel.tracker.model.ParcelRequest;
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
