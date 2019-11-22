package com.example.springboot.consul.demo.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
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
	String appProperty;
	
	@Value("${common.property}")
	String commonProperty;

	@Autowired
	private DiscoveryClient discoveryClient;

	private Map<String, URI> map = new HashMap<>();

	@RequestMapping("/health")
	public String health() {
		System.out.println(appProperty);
		System.out.println(commonProperty);
		return "Healthy";
	}

	@RequestMapping("/services")
	public Map<String, URI> getAllServices() {
		discoveryClient.getServices().forEach(s -> {
			Optional<URI> uri = discoveryClient.getInstances(s).stream().map(si -> si.getUri()).findFirst();
			map.put(s, uri == null ? null : uri.get());

		});
		return map;
	}

}
