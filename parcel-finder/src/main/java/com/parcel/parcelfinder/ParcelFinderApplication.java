package com.parcel.parcelfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ParcelFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelFinderApplication.class, args);
	}

}
