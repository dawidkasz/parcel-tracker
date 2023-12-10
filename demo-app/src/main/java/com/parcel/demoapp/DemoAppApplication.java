package com.parcel.demoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@RestController
@RefreshScope
public class DemoAppApplication {
	@Value("${hello-from:Mum}")
	private String test;
	@Value("${random-number:5}")
	private Integer number;

	@Autowired
	private GreetingClient greetingClient;
	@GetMapping("/")
	public String hello() {
		return "Hello " + test + number;
	}

	@RequestMapping("/test")
	public String greeting() {
		return String.format("Demo app: Hello from '%s'!", greetingClient.greeting());
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoAppApplication.class, args);
	}
}

