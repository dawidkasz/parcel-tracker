package com.parcel.testservice;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class TestApplication implements GreetingController {
    @Value("${spring.application.name}")
    private String appName;
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Override
    public String greeting() {
        return String.format(
                "Hello from '%s'!", appName);
    }
}