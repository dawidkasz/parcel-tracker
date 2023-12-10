package com.parcel.reportgenerator.client;

import com.parcel.reportgenerator.domain.Parcel;
import feign.RequestLine;

import java.util.List;

public interface PackageClient {
    @RequestLine("GET /api/packages")
    List<Parcel> getParcels();
}
