package com.parcel.tracker.controller;

import com.parcel.tracker.model.Parcel;
import com.parcel.tracker.model.Status;
import com.parcel.tracker.repository.ParcelRepository;
import com.parcel.tracker.resource.ParcelRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class ParcelController {

    private final ParcelRepository parcelRepository;

    public ParcelController(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @GetMapping("/parcel")
    public ResponseEntity<List<Parcel>> getAllParcels() {
        return ResponseEntity.ok(this.parcelRepository.findAll());
    }

    @PostMapping("/parcel")
    public ResponseEntity<Parcel> createParcel(@RequestBody ParcelRequest parcelRequest) {

        Parcel parcel = new Parcel();
        parcel.setName(parcelRequest.getName());

        // Jeśli użytkownik nie podał statusów, ustaw domyślnie pustą listę.
        List<Status> statuses = parcelRequest.getStatuses() != null ? parcelRequest.getStatuses() : Collections.emptyList();
        parcel.setStatuses(statuses);

        return ResponseEntity.status(200).body(this.parcelRepository.save(parcel));
    }

    @GetMapping("/parcel/{id}")
    public ResponseEntity<Parcel> getParcelById(@PathVariable String id) {

        Optional<Parcel> optionalParcel = this.parcelRepository.findById(id);

        return optionalParcel.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(new Parcel("Parcel not found", null)));
    }

    @DeleteMapping("/parcel/{id}")
    public ResponseEntity<String> deleteParcelById(@PathVariable String id) {

        Optional<Parcel> optionalParcel = this.parcelRepository.findById(id);

        if (optionalParcel.isPresent()) {
            this.parcelRepository.deleteById(id);
            return ResponseEntity.ok("Parcel deleted successfully.");
        } else {
            return ResponseEntity.ok("The parcel with id: " + id + " was not found.");
        }
    }

    @PostMapping("/parcel/{id}/status")
    public ResponseEntity<Parcel> addStatusToParcel(@PathVariable String id, @RequestBody Status newStatus) {

        Optional<Parcel> optionalParcel = this.parcelRepository.findById(id);

        if (optionalParcel.isPresent()) {
            Parcel parcel = optionalParcel.get();
            List<Status> statuses = parcel.getStatuses();
            statuses.add(newStatus);
            parcel.setStatuses(statuses);

            return ResponseEntity.ok(this.parcelRepository.save(parcel));
        } else {
            return ResponseEntity.ok(new Parcel("Parcel not found", null));
        }
    }

    @GetMapping("/parcel/{id}/statuses")
    public ResponseEntity<List<Status>> getParcelStatuses(@PathVariable String id) {

        Optional<Parcel> optionalParcel = this.parcelRepository.findById(id);

        return optionalParcel.map(parcel -> ResponseEntity.ok(parcel.getStatuses()))
                .orElseGet(() -> ResponseEntity.ok(null));
    }
}
