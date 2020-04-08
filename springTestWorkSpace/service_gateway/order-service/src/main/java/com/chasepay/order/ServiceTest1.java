package com.chasepay.order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class ServiceTest1 {

	public static void main(String[] args) {
		
		SpringApplication.run(ServiceTest1.class, args);
		
	}
	

	
	
}
