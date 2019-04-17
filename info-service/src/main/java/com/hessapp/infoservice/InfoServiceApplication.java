package com.hessapp.infoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@EnableDiscoveryClient
@SpringBootApplication
public class InfoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoServiceApplication.class, args);
	}
}
