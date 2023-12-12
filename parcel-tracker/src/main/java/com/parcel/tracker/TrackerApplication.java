package com.parcel.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@RestController
@RefreshScope
public class TrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackerApplication.class, args);
    }

}