package com.parcel.reportgenerator.domain;

import java.util.List;

public interface ParcelProvider {
    List<Parcel> getParcels(ReportRequest request);
}
