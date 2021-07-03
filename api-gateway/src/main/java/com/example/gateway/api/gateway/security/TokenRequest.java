package com.example.gateway.api.gateway.security;

import lombok.Data;

@Data
public class TokenRequest {
    private String username;
    private String password;
}
