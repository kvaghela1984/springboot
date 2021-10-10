package com.example.oauth2.tutorial.webClientoauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class MessageController {
    @Autowired
    private WebClient webClient;

    @GetMapping(value = "/messages")
    public Mono<String[]> getMessages(){
        return webClient.get()
                .uri("localhost:8081/messages")
                .retrieve()
                .bodyToMono(String[].class);

    }
}
