package com.example.tutorial.reactiveapi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

//@Data
//@Entity()
//@Table(name = "employees")
//public class Employee {
//    @Id
//    @Column(name = "employee_id")
//    private long employeeId;
//    @Column(name = "employee_first_name")
//    private String employeeFirstName;
//    @Column(name = "employee_last_name")
//    private String employeeLastName;
//    @Column(name = "employee_email")
//    private String employeeEmail;
//    @Column(name = "employee_department")
//    private String employeeDepartment;
//}

@Data
@Table(name = "employees")
public class Employee {
    @Id
    @Column("employee_id")
    private Long employeeId;
    @Column("employee_first_name")
    private String employeeFirstName;
    @Column("employee_last_name")
    private String employeeLastName;
    @Column("employee_email")
    private String employeeEmail;
    @Column("employee_department")
    private String employeeDepartment;
}
