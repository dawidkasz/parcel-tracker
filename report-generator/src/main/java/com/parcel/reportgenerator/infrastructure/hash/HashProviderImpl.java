package com.parcel.reportgenerator.infrastructure.hash;

import com.parcel.reportgenerator.domain.ContentHashProvider;
import com.parcel.reportgenerator.domain.Parcel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HashProviderImpl implements ContentHashProvider {
    @Override
    public long getContentHash(List<Parcel> parcels) {
        return parcels.stream().map(Parcel::hashCode).reduce(0, Integer::sum);
    }
}
