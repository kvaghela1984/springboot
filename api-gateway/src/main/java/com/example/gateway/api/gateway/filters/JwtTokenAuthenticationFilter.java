package com.example.gateway.api.gateway.filters;

import com.example.gateway.api.gateway.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter implements WebFilter {
    private final JwtUtil jwtUtil;
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        String token = resolveToken(serverWebExchange.getRequest());
        if(StringUtils.hasText(token)){
            Authentication authentication = jwtUtil.getAuthentication(token);
            jwtUtil.validateToken(token);
            webFilterChain.filter(serverWebExchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }
        return webFilterChain.filter(serverWebExchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TOKEN_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }
}
