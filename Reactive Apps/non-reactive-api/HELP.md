# DB DDL
```sql
CREATE TABLE employees (
    employee_id bigint generated by default as identity(start with 1) NOT NULL,
    employee_first_name varchar(200) NOT NULL,
    employee_last_name varchar(100) NOT NULL,
    employee_email varchar(1000) NOT NULL,
    employee_department varchar(200) NOT NULL, 
    PRIMARY KEY (employee_id)
);
```

