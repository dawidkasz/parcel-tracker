package com.parcel.parcelfinder.infrastructure.web;

import com.parcel.parcelfinder.domain.Parcel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface PackageClient {
    @GetMapping(value="/parcel/search")
    List<Parcel> getParcels(@RequestParam("q") String query);

    default List<Parcel> getParcels() {
        return getParcels("");
    }
}