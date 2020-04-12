package com.example.springboot.zuulproxy;

import com.example.springboot.zuulproxy.filters.PostFilter;
import com.example.springboot.zuulproxy.filters.PreFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ZuulProxyExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulProxyExampleApplication.class, args);
	}

	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}

}
