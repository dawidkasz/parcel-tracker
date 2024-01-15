package com.parcel.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class,  args);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("parcel-finder", r -> r.path("/find/**")
						.filters(f -> f.rewritePath("/find(/(?<segment>.*))?", "/${segment}"))
						.uri("lb://parcel-finder"))
				.route("report-generator", r -> r.path("/report/**")
						.filters(f -> f.rewritePath("/report(/(?<segment>.*))?", "/${segment}"))
						.uri("lb://report-generator"))
				.route("parcel-tracker", r -> r.path("/track/**")
						.filters(f -> f.rewritePath("/track(/(?<segment>.*))?", "/${segment}"))
						.uri("lb://parcel-tracker"))
				.build();
	}
}
