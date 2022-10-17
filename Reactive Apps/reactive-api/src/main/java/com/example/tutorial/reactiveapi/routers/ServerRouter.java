package com.example.tutorial.reactiveapi.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ServerRouter {
    @Bean
    public RouterFunction<ServerResponse> route(EmployeeHandler employeeHandler) {

        return RouterFunctions
                .route(GET("/employee/{employeeId}").and(accept(MediaType.APPLICATION_JSON)), employeeHandler::getEmployee)
                .andRoute(GET("/employee").and(accept(MediaType.APPLICATION_JSON)), employeeHandler::getAllEmployees)
                .andRoute(POST("/employee").and(accept(MediaType.APPLICATION_JSON)), employeeHandler::saveEmployee);
    }
}
