package com.parcel.parcelfinder.infrastructure.web;

import com.parcel.parcelfinder.application.ParcelLister;
import com.parcel.parcelfinder.domain.Parcel;
import com.parcel.parcelfinder.domain.ParcelStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/parcel")
public class ParcelFinderController {
    private final ParcelLister parcelLister;

    @GetMapping("/search")
    public List<ParcelResponse> findParcels(@RequestParam("q") String query) {
        return maptoParcelResponseList(parcelLister.search(query));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listParcels(@RequestParam("page") int pageId) {
        try {
            return ResponseEntity.ok(maptoParcelResponseList(parcelLister.findAll(pageId)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    private List<ParcelResponse> maptoParcelResponseList(List<Parcel> parcels) {
        return parcels.stream()
                .map(ParcelResponse::from)
                .toList();
    }

    public record ParcelResponse(String id, String carrier, List<ParcelStatus> history) {
        public static ParcelResponse from(Parcel parcel) {
            return new ParcelResponse(
                parcel.parcelId().id(),
                parcel.carrier().name(),
                parcel.statusHistory()
            );
        }
    }

    public record ErrorResponse(String detail) {
    }
}
