package com.poc.ProductMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.poc.ProductMicroservice", "com.poc.commonSecurities"})
public class ProductMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductMicroserviceApplication.class, args);
	}

}
