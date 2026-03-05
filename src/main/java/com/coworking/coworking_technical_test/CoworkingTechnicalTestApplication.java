package com.coworking.coworking_technical_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoworkingTechnicalTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoworkingTechnicalTestApplication.class, args);
	}

}
