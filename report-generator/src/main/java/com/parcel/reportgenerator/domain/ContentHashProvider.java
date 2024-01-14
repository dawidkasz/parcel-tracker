package com.parcel.reportgenerator.domain;

import java.util.List;

public interface ContentHashProvider {
    long getContentHash(List<Parcel> parcels);
}
