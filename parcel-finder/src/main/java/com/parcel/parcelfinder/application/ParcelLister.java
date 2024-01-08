package com.parcel.parcelfinder.application;

import com.parcel.parcelfinder.domain.Parcel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelLister {
    private final ParcelRepository parcelRepository;

    public List<Parcel> search(String query) {
        return parcelRepository.search(query);
    }
}
