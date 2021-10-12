package com.example.gateway.securedcloudgateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class LoggingGlobalFilter implements GlobalFilter {

    final Logger logger = LoggerFactory.getLogger(LoggingGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = (Route) exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        logger.info(route.toString());
        Instant from = Instant.now();
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    ServerHttpResponse response = exchange.getResponse();
                    logger.info("ENDPOINT: {}, STATUS: {}, RESPONSETIME: {}, HTTPMETHOD: {}, URI: {}, BACKENDHOST: {}", route.getId(), response.getStatusCode(), Instant.now().toEpochMilli() - from.toEpochMilli(), exchange.getRequest().getMethodValue(), exchange.getRequest().getPath(), route.getUri());
                })
        );
    }
}
