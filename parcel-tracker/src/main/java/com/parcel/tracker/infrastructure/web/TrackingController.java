package com.parcel.tracker.infrastructure.web;

import com.parcel.tracker.application.Tracker;
import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.DomainException;
import com.parcel.tracker.domain.Parcel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tracking")
public class TrackingController {
    private final Tracker tracker;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/start")
    public void addParcelToTracking(@RequestBody StartParcelTrackingRequest request) throws DomainException {
        tracker.startTracking(request.toParcel());
    }

    public record StartParcelTrackingRequest(String id, Carrier carrier, String description) {
        public Parcel toParcel() {
            return new Parcel(id, carrier, description);
        }
    }

    @ExceptionHandler(DomainException.class)
    private ResponseEntity<Object> handleConflict(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
