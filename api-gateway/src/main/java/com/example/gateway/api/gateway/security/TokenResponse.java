package com.example.gateway.api.gateway.security;

import lombok.Data;

import java.util.Date;
@Data
public class TokenResponse {
    private String access_token;
    private Date issuedAt;
    private Date expiresAt;
}
