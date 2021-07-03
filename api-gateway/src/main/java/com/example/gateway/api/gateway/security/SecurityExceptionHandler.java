package com.example.gateway.api.gateway.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class SecurityExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable ex) {
        if(ex instanceof TokenExpiredException || ex instanceof AuthenticationException){
            serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return serverWebExchange.getResponse().setComplete();
        } else {
            return Mono.error(ex);
        }
    }
}
