package com.parcel.reportgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestRaportGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.from(RaportGeneratorApplication::main).with(TestRaportGeneratorApplication.class).run(args);
	}

}
