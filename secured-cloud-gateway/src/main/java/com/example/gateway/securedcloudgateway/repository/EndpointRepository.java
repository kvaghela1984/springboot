package com.example.gateway.securedcloudgateway.repository;

import com.example.gateway.securedcloudgateway.entities.Endpoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EndpointRepository extends CrudRepository<Endpoint, String> {
}
