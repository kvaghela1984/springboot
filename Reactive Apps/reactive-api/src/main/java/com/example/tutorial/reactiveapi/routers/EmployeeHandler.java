package com.example.tutorial.reactiveapi.routers;

import com.example.tutorial.reactiveapi.models.Employee;
import com.example.tutorial.reactiveapi.repositories.EmployeeReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class EmployeeHandler {

    @Autowired
    private EmployeeReactiveRepository employeeReactiveRepository;

    public Mono<ServerResponse> getEmployee(ServerRequest request) {
        return employeeReactiveRepository.findById(Long.valueOf(request.pathVariable("employeeId")))
                .delayElement(Duration.ofSeconds(1))
                .flatMap(employee -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(employee))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(
                        e -> {
                            System.out.println(e.getMessage());
                            System.out.println(e.getCause());
                            System.out.println(e.getStackTrace());
                            return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build();
                        }
                );
    }

    public Mono<ServerResponse> getAllEmployees(ServerRequest request) {
        return ServerResponse.ok().body(employeeReactiveRepository.findAll()
                .delayElements(Duration.ofMillis(50)), Employee.class);
    }

    public Mono<ServerResponse> saveEmployee(ServerRequest request) {
        return request.bodyToMono(Employee.class)
                .doOnNext(employee -> employeeReactiveRepository.save(employee).subscribe()) //without subscribe save is not executed.
                .delayElement(Duration.ofSeconds(2))
                .flatMap(employee -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(employee))
                .onErrorResume(
                        e -> {
                            System.out.println(e.getMessage());
                            System.out.println(e.getCause());
                            System.out.println(e.getStackTrace());
                            return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build();
                        }
                );
    }
}
