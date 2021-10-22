package com.example.gateway.securedcloudgateway.config;

import com.example.gateway.securedcloudgateway.entities.Endpoint;
import com.example.gateway.securedcloudgateway.repository.EndpointRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@Configuration
public class GatewayConfig {
    @Autowired
    private EndpointRepository endpointRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        endpointRepository.findAll().forEach(
                endpoint -> {
                    routes.route(endpoint.getId(),
                            r-> r.path(endpoint.getPath())
                                    .and().method(endpoint.getMethod())
                                    .and().metadata(RESPONSE_TIMEOUT_ATTR, 200)
                                    .metadata(CONNECT_TIMEOUT_ATTR, 200)
                                    .uri(endpoint.getHost())
                    );
                }
        );
        return routes.build();
    }
}
