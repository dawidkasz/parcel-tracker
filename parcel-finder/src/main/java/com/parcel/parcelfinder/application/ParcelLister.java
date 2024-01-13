package com.parcel.parcelfinder.application;

import com.parcel.parcelfinder.domain.Parcel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@RequiredArgsConstructor
public class ParcelLister {
    private final ParcelRepository parcelRepository;

    public List<Parcel> search(String query) {
        return parcelRepository.search(query);
    }

    public List<Parcel> findAll(int pageId) {
        checkArgument(pageId > 0, "Page number must be greater than 0.");

        return parcelRepository.findAll(pageId - 1);
    }
}
