package com.example.oauth2.tutorial.webClientoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebClientOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(WebClientOauth2Application.class, args);
    }

}
