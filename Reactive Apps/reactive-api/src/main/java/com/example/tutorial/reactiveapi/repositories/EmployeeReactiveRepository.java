package com.example.tutorial.reactiveapi.repositories;

import com.example.tutorial.reactiveapi.models.Employee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface EmployeeReactiveRepository extends R2dbcRepository<Employee, Long> {

}
