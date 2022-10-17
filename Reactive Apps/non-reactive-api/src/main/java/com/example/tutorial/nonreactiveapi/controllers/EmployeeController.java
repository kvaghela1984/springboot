package com.example.tutorial.nonreactiveapi.controllers;

import com.example.tutorial.nonreactiveapi.models.Employee;
import com.example.tutorial.nonreactiveapi.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeReactiveRepository;


    @GetMapping("/{id}")
    private Employee getEmployeeById(@PathVariable long id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return employeeReactiveRepository.findById(id).get();
    }

    @GetMapping
    private List<Employee> getAllEmployees()  {
        List<Employee> employees = new ArrayList<Employee>();
        employeeReactiveRepository.findAll().forEach(employee -> {
            try {
                Thread.sleep(50);
                employees.add(employee);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return employees;
    }

    @PostMapping
    private Employee saveEmployee(@RequestBody Employee employee) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return employeeReactiveRepository.save(employee);
    }
}
