package com.parcel.reportgenerator.config;

import com.parcel.reportgenerator.client.PackageClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public PackageClient packageClient() {
        return Feign.builder()
                .decoder(new GsonDecoder())
                .target(PackageClient.class, "http://localhost:3080");
    }
}
