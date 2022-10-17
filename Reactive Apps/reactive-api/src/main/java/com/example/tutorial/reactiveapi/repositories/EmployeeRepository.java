package com.example.tutorial.reactiveapi.repositories;

import com.example.tutorial.reactiveapi.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
