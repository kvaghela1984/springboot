package com.example.tutorial.nonreactiveapi.repositories;

import com.example.tutorial.nonreactiveapi.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
