package com.parcel.tracker.infrastructure.carrierclient;

import com.parcel.tracker.application.carrier.CarrierClient;
import com.parcel.tracker.application.carrier.CarrierClientException;
import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.domain.ParcelStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryCarrierClient implements CarrierClient {
    private final List<String> nextStatuses = List.of("received", "preparing", "in_transit", "delivered");
    private final List<String> nextDescriptions = List.of(
        "Received parcel delivery request.",
        "Parcel is being prepared.",
        "Package is in transit.",
        "Package has been delivered."
    );

    @Override
    public ParcelStatus checkParcelStatus(Parcel parcel) throws CarrierClientException {
        log.info("Checking parcel {} status for InMemory", parcel.getId());

        if (parcel.latestStatus().isEmpty()) {
            return ParcelStatus.of(nextStatuses.get(0), Instant.now(), nextDescriptions.get(0));
        }

        int idx = nextStatuses.indexOf(parcel.latestStatus().get().getStatus());
        if (idx == -1) {
            throw new CarrierClientException(String.format("Unrecognized parcel status %s", parcel.latestStatus().get()));
        }

        if (idx == nextStatuses.size() - 1) {
            return parcel.latestStatus().get();
        }

        return ParcelStatus.of(nextStatuses.get(idx + 1), Instant.now(), nextDescriptions.get(idx + 1));
    }

    @Override
    public Carrier getCarrier() {
        return Carrier.IN_MEMORY;
    }
}
