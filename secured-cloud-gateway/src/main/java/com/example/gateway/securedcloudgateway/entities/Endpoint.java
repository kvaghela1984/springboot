package com.example.gateway.securedcloudgateway.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "endpoint")
@Data
@Entity
public class Endpoint {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "method")
    private String method;

    @Column(name = "path")
    private String path;

    @Column(name = "host")
    private String host;

    @Column(name = "response_timeout")
    private int responseTimeout;

    @Column(name = "policy")
    private String policy;
}
