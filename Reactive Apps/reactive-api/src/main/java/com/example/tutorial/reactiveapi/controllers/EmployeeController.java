package com.example.tutorial.reactiveapi.controllers;

import com.example.tutorial.reactiveapi.models.Employee;
import com.example.tutorial.reactiveapi.repositories.EmployeeReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeReactiveRepository employeeReactiveRepository;


    @GetMapping("/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable long id) {
        return employeeReactiveRepository.findById(id);
    }

    @GetMapping
    private Flux<Employee> getAllEmployees() {
        return employeeReactiveRepository.findAll();
    }

    @PostMapping
    private Mono<Employee> saveEmployee(@RequestBody Employee employee) {
        return employeeReactiveRepository.save(employee)
                .onErrorContinue((e, o) -> {
                    System.out.println(e.getMessage());
                    System.out.println(o.toString());
                });
    }

//    @PostMapping
//    private Mono<Employee> updateEmployee(ServerRequest request) {
//        return request.bodyToMono(Employee.class)
//                .flatMap(employee -> {
//                            return employeeReactiveRepository.save(employee)
//                                    .onErrorContinue((e, o) -> {
//                                        System.out.println(e.getMessage());
//                                        System.out.println(o.toString());
//                                    });
//                        }
//                );
//    }
}
