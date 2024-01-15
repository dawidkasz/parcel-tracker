package com.parcel.tracker.infrastructure.web;

import com.parcel.tracker.application.Tracker;
import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.DomainException;
import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.domain.ParcelId;
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

    @PostMapping("/start")
    public ResponseEntity<TrackingResponse> addParcelToTracking(@RequestBody StartParcelTrackingRequest request) throws DomainException {
        tracker.startTracking(request.toParcel());

        return TrackingResponse.ok();
    }

    public record StartParcelTrackingRequest(String id, Carrier carrier) {
        public Parcel toParcel() {
            return new Parcel(ParcelId.of(id), carrier);
        }
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<TrackingResponse> handleDomainException(DomainException ex) {
        return TrackingResponse.error(ex.getMessage());
    }

    public record TrackingResponse(String detail) {
        public static ResponseEntity<TrackingResponse> ok() {
            return ResponseEntity.ok(new TrackingResponse("success"));
        }

        public static ResponseEntity<TrackingResponse> error(String detail) {
            return ResponseEntity.badRequest().body(new TrackingResponse(detail));
        }
    }
}
