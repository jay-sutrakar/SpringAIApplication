package com.ai.springaidemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringaidemoApplication {
	private static final Logger log = LoggerFactory.getLogger(SpringaidemoApplication.class);
	static void main(String[] args) {
		SpringApplication.run(SpringaidemoApplication.class, args);
		log.info("environment variables : ", System.getenv().entrySet().stream()
				.toList());
	}

}
