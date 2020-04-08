package com.chasepay.purchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PurchaseServiceStart {
	
	public static void main(String[] args) {
		
		SpringApplication.run(PurchaseServiceStart.class, args);
		
	}

}
