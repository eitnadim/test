package com.eit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(basePackages = "com.eit.gateway.repository")
public class EitGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EitGatewayApplication.class, args);
	}

}
