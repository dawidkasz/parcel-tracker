package com.parcel.demoapp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("test-service")
public interface GreetingClient {
    @RequestMapping("/")
    String greeting();
}
