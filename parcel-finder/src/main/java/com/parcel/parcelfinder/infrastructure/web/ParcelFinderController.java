package com.parcel.parcelfinder.infrastructure.web;

import com.parcel.parcelfinder.application.ParcelLister;
import com.parcel.parcelfinder.domain.Parcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<Parcel> findParcels(@RequestParam("q") String query) {
        return parcelLister.search(query);
    }
}
