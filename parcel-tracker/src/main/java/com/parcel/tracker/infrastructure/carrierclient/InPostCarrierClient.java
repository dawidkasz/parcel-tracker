package com.parcel.tracker.infrastructure.carrierclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parcel.tracker.application.carrier.CarrierClient;
import com.parcel.tracker.application.carrier.CarrierClientException;
import com.parcel.tracker.domain.Carrier;
import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.domain.ParcelStatus;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class InPostCarrierClient implements CarrierClient {
    private static final String BASE_URL = "https://api-shipx-pl.easypack24.net/v1";

    private final RestTemplate restTemplate;
    private final Map<String, InpostStatus> statusMap = new HashMap<>();

    @Override
    public ParcelStatus checkParcelStatus(Parcel parcel) throws CarrierClientException {
        log.info("Checking parcel {} status for InPost", parcel.getId());

        String resourceUrl = BASE_URL + String.format("/tracking/%s", parcel.getId());

        try {
            var response = restTemplate.getForObject(resourceUrl, InpostParcelTrackingResponse.class);
            Objects.requireNonNull(response);

            InpostStatus inpostStatus = Optional.ofNullable(statusMap.get(response.getStatus()))
                    .orElseThrow(() -> new CarrierClientException(String.format("Parcel status %s not recognized", response.getStatus())));

            return ParcelStatus.of(response.getStatus(), response.getUpdatedAt(), inpostStatus.getDescription());
        } catch (Exception e) {
            throw new CarrierClientException(e);
        }
    }

    @Override
    public Carrier getCarrier() {
        return Carrier.INPOST;
    }

    @PostConstruct
    private void initializeStatuses() {
        log.info("Initializing inpost status map");

        String resourceUrl = BASE_URL + "/statuses";
        var response = restTemplate.getForObject(resourceUrl, InpostStatusResponse.class);

        Objects.requireNonNull(response);
        response.getItems().forEach(status -> statusMap.put(status.getName(), status));
    }

    @Data
    private static class InpostParcelTrackingResponse {
        @JsonProperty("tracking_number")
        private String trackingNumber;

        private String status;

        @JsonProperty("updated_at")
        private Instant updatedAt;
    }

    @Data
    private static class InpostStatusResponse {
        private List<InpostStatus> items;
    }

    @Data
    private static class InpostStatus {
        private String name;
        private String title;
        private String description;
    }
}