package com.parcel.tracker.carrier;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.tracker.Tracker;
import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class Ship24Carrier implements Carrier {

    private static final String SHIP24_API_URL = "https://api.ship24.com/public/v1/trackers/track";
    private static final String API_KEY = "apik_ilPNcAM4GFtvy6vgXeeYuCD9Azzxkq";

    private final ParcelRepository parcelRepository;

    @Override
    public void startTracking(Tracker tracker) {
        try {
            sendAPIRequest(tracker.getParcelId());

            Parcel newParcel = new Parcel(
                    tracker.getParcelId(),
                    tracker.getParcel().getCarrierName(),
                    tracker.getParcel().getDescription(),
                    new ArrayList<>()
            );
            parcelRepository.save(newParcel);

            log.info("Ship24 tracking started for parcel: {}", tracker.getParcelId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkParcelStatus(Tracker tracker) {
        try {
            String responseString = sendAPIRequest(tracker.getParcelId());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseString);

            JsonNode trackingsNode = rootNode.path("data").path("trackings");
            if (trackingsNode.isArray() && trackingsNode.size() > 0) {
                JsonNode trackingNode = trackingsNode.get(0);
                JsonNode shipmentNode = trackingNode.path("shipment");
                String statusMilestone = shipmentNode.path("statusMilestone").asText();
                log.info("Status for parcel {}: ", tracker.getParcelId(), statusMilestone);
                tracker.updateStatus(statusMilestone);
            } else {
                log.info("No tracking data found in the JSON response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String sendAPIRequest(String parcelId) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder uriBuilder = new URIBuilder(SHIP24_API_URL);
            URI uri = uriBuilder.build();

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeader("Authorization", "Bearer " + API_KEY);
            httpPost.setHeader("Content-Type", "application/json; charset=utf-8");

            String curlRequestBody = String.format("{\n" +
                    "  \"trackingNumber\": \"%s\",\n" +
                    "  \"shipmentReference\": \"c6e4fef4-a816-b68f-4024-3b7e4c5a9f81\",\n" +
                    "  \"originCountryCode\": \"CN\",\n" +
                    "  \"destinationCountryCode\": \"US\",\n" +
                    "  \"destinationPostCode\": \"94901\",\n" +
                    "  \"shippingDate\": \"2021-03-01T11:09:00.000Z\",\n" +
                    "  \"courierCode\": [\n" +
                    "    \"us-post\"\n" +
                    "  ],\n" +
                    "  \"courierName\": \"USPS Standard\",\n" +
                    "  \"trackingUrl\": \"https://tools.usps.com/go/TrackConfirmAction?tLabels=%s\",\n" +
                    "  \"orderNumber\": \"DF14R2022\"\n" +
                    "}", parcelId, parcelId);
            httpPost.setEntity(new StringEntity(curlRequestBody, ContentType.APPLICATION_JSON));

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }

        return null;
    }
}
