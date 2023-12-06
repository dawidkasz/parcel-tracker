package com.parcel.tracker.controller;

import com.parcel.tracker.model.Parcel;
import com.parcel.tracker.repository.ParcelRepository;
import com.parcel.tracker.resource.ParcelRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        parcel.setDescription(parcelRequest.getDescription());

        return ResponseEntity.status(200).body(this.parcelRepository.save(parcel));
    }

    @GetMapping("/parcel/{id}")
    public ResponseEntity getParcelById(@PathVariable String id) {

        Optional<Parcel> parcel = this.parcelRepository.findById(id);

        if(parcel.isPresent()) {
            return ResponseEntity.ok(parcel.get());
        } else {
            return ResponseEntity.ok("The parcel with id: " + id + " was not found.");
        }
    }

    @DeleteMapping("/parcel/{id}")
    public ResponseEntity deleteParcelById(@PathVariable String id) {

        Optional<Parcel> parcel = this.parcelRepository.findById(id);

        if(parcel.isPresent()) {
            this.parcelRepository.deleteById(id);
            return ResponseEntity.ok("Success.");
        } else {
            return ResponseEntity.ok("The parcel with id: " + id + " was not found.");
        }
    }
}
