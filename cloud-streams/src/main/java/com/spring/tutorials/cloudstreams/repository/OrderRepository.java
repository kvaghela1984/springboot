package com.spring.tutorials.cloudstreams.repository;

import com.spring.tutorials.cloudstreams.data.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {

}