package com.example.gateway.api.gateway.controllers;

import com.example.gateway.api.gateway.security.JwtUtil;
import com.example.gateway.api.gateway.security.TokenRequest;
import com.example.gateway.api.gateway.security.TokenResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TokenController {

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final JwtUtil jwtUtil;

    public TokenController(ReactiveAuthenticationManager reactiveAuthenticationManager, JwtUtil jwtUtil) {
        this.reactiveAuthenticationManager = reactiveAuthenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "/auth/token", consumes = {MediaType.APPLICATION_JSON_VALUE})
    Mono<TokenResponse> token(@RequestBody Mono<TokenRequest> request){

        return request
                .flatMap(tokenRequest -> this.reactiveAuthenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(),tokenRequest.getPassword()))
                        .map(this.jwtUtil::createToken));
    }
}
