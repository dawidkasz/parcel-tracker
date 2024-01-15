package com.parcel.reportgenerator.helper;

import com.parcel.reportgenerator.domain.Parcel;

import java.util.List;

public class StubsFactory {
    public static List<Parcel> getParcels() {
        return List.of(
                new Parcel("test", "carr", List.of()),
                new Parcel("test2", "carr2", List.of()),
                new Parcel("test3", "carr3", List.of())
        );
    }
}
