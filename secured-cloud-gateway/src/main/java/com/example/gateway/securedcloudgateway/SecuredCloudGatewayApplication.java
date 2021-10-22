package com.example.gateway.securedcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
//@EntityScan("com.example.gateway.securedcloudgateway.entities")
public class SecuredCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuredCloudGatewayApplication.class, args);
	}

}
