package com.hassapp.api;

import com.hassapp.api.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;

@CrossOrigin
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class LoginServiceApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(LoginServiceApplication.class);

	@Resource
	FileStorageService fileStorageService;

	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			fileStorageService.init();
		}catch (Exception e){
			log.info("Upload dosyasi zaten var ! " + e.getMessage());
		}
	}
}
