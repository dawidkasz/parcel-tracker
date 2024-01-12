package com.parcel.reportgenerator.config;

import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.optionals.OptionalDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public Decoder feignDecoder() {
        return new OptionalDecoder(new GsonDecoder());
    }
}
