package com.example.gateway.api.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("/hello")
    Mono<String> hello(){
        return Mono.just("Hello");
    }
}
