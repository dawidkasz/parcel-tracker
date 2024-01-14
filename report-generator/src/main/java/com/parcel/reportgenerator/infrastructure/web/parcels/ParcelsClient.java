package com.parcel.reportgenerator.infrastructure.web.parcels;

import com.parcel.reportgenerator.domain.Parcel;
import com.parcel.reportgenerator.domain.ParcelProvider;
import com.parcel.reportgenerator.domain.ReportRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "parcel-finder")
public interface ParcelsClient extends ParcelProvider {
    @CircuitBreaker(name = "reportGeneratorService", fallbackMethod = "getParcelsFallback")
    @GetMapping("/parcel/search")
    List<Parcel> getParcelsByQuery(@RequestParam(value = "q") String query);

    @Override
    default List<Parcel> getParcels(ReportRequest request) {
        return getParcelsByQuery(request.query());
    }

    default List<Parcel> getParcelsByQuery() {
        return getParcelsByQuery("");
    }

    default List<Parcel> getParcelsFallback(Throwable throwable) {

        return List.of();
    }
}
