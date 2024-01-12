package com.parcel.reportgenerator.client;

import com.parcel.reportgenerator.domain.Parcel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@FeignClient("parcel-finder")
public interface PackageClient {
    @CircuitBreaker(name="reportGeneratorService", fallbackMethod = "getParcelsFallback")
    @GetMapping("/parcel/search")
    List<Parcel> getParcels(@RequestParam(value="q") String query);

    default List<Parcel> getParcels() {
        return getParcels("");
    }
    default List<Parcel> getParcelsFallback(Throwable throwable) {
        return Collections.emptyList();
    }
}
