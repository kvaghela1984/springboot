package com.example.springboot.consul.demo.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class Health {

	@Value("${my.property}")
	String value;
	
	@Autowired
    private DiscoveryClient discoveryClient;

	@RequestMapping("/health")
	public String health() {
		
		Optional<URI> findFirst = discoveryClient.getInstances("consul-demo")
        .stream()
        .map(si -> si.getUri())
        .findFirst();
		
		System.out.println(findFirst.get());
		
		System.out.println(value);
		return "Healthy";
	}

}
