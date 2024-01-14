package com.parcel.parcelfinder;

import com.parcel.parcelfinder.application.ParcelLister;
import com.parcel.parcelfinder.application.ParcelRepository;
import com.parcel.parcelfinder.domain.Carrier;
import com.parcel.parcelfinder.domain.Parcel;
import com.parcel.parcelfinder.domain.ParcelId;
import com.parcel.parcelfinder.domain.ParcelStatus;
import com.parcel.parcelfinder.infrastructure.web.ParcelFinderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static com.parcel.parcelfinder.infrastructure.web.ParcelFinderController.ParcelResponse;
import static com.parcel.parcelfinder.infrastructure.web.ParcelFinderController.ErrorResponse;


public class ParcelListerTest {
    private ParcelRepository parcelRepository;
    private ParcelFinderController parcelFinderController;

    @BeforeEach
    void setup() {
        parcelRepository = mock(ParcelRepository.class);
        parcelFinderController = new ParcelFinderController(new ParcelLister(parcelRepository));
    }

    @Test
    void should_find_parcels_by_query() {
        // given:
        var timestamp = Instant.now();
        var parcel1 = new Parcel(
                ParcelId.of("123"),
                Carrier.INPOST,
                List.of(new ParcelStatus("status1", timestamp, "desc"))
        );


        when(parcelRepository.search("123")).thenReturn(List.of(parcel1));

        //when:
        var response = parcelFinderController.findParcels("123");

        //then:
        var expectedResponse = List.of(new ParcelResponse(
            "123",
            "INPOST",
            List.of(new ParcelStatus("status1", timestamp, "desc"))
        ));
        assertEquals(expectedResponse, response);
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_list_all_parcels() {
        // given:
        var timestamp = Instant.now();
        var parcel1 = new Parcel(
                ParcelId.of("123"),
                Carrier.INPOST,
                List.of(new ParcelStatus("status1", timestamp, "desc"))
        );


        when(parcelRepository.findAll(0)).thenReturn(List.of(parcel1));

        //when:
        var response = (List<ParcelResponse>) parcelFinderController.listParcels(1).getBody();

        //then:
        var expectedResponse = List.of(new ParcelResponse(
                "123",
                "INPOST",
                List.of(new ParcelStatus("status1", timestamp, "desc"))
        ));
        assertEquals(expectedResponse, response);
    }

    @Test
    void should_throw_an_error_when_listing_parcels_for_invalid_page() {
        //when:
        parcelFinderController.listParcels(0);

        //then:
        var response = (ErrorResponse) parcelFinderController.listParcels(0).getBody();

        assertEquals(response.detail(), "Page number must be greater than 0.");
    }

}
