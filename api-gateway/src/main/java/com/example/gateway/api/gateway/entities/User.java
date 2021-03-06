package com.example.gateway.api.gateway.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class User {
    @Id
    private String username;
    private String password;
    private String email;
}
