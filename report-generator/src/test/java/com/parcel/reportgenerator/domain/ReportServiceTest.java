package com.parcel.reportgenerator.domain;

import com.parcel.reportgenerator.helper.StubsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReportServiceTest {
    private final ReportGenerator reportGenerator = mock(ReportGenerator.class);
    private final ParcelProvider parcelProvider = mock(ParcelProvider.class);
    private final ReportRepository reportRepository = mock(ReportRepository.class);
    private final ContentHashProvider hashProvider = mock(ContentHashProvider.class);
    private ReportService service;


    @BeforeEach
    public void init() {
        service = new ReportService(reportGenerator, parcelProvider, reportRepository, hashProvider);
    }

    @Test
    void shouldCreateNewReport_WhenNewContent() {
        //given
        var parcels = StubsFactory.getParcels();
        var request = new ReportRequest("qr");
        when(parcelProvider.getParcels(any())).thenReturn(parcels);

        //when
        service.createReport(request);

        //then
        verify(reportGenerator, times(1)).generateReport(request, parcels);
    }

    @Test
    void shouldNotCreateNewReport_WhenContentPresentInRepository() {
        //given
        var parcels = StubsFactory.getParcels();
        var request = new ReportRequest("qr");
        when(parcelProvider.getParcels(any())).thenReturn(parcels);
        when(reportRepository.findIdByContentHash(anyLong())).thenReturn(Optional.of(ReportId.nextIdentity()));

        //when
        service.createReport(request);

        //then
        verify(reportGenerator, times(0)).generateReport(request, parcels);
    }

}