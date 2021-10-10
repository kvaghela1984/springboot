package com.example.oauth2.tutorial.webClientoauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Component
public class WebClientChonJob {

    private static final String RESOURCE_URI = "localhost:8081/messages";
    Logger logger = LoggerFactory.getLogger(WebClientChonJob.class);
    @Autowired
    private WebClient webClient;

    @Scheduled(fixedRate = 5000)
    public void logResourceServiceResponse() {

        webClient.get()
                .uri(RESOURCE_URI)
                .retrieve()
                .bodyToMono(String[].class)
                .subscribe(array -> Arrays.stream(array).iterator().forEachRemaining(logger::info));
    }
}
