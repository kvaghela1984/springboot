package com.spring.tutorials.cloudstreams.controller;


import com.spring.tutorials.cloudstreams.data.Order;
import com.spring.tutorials.cloudstreams.repository.OrderRepository;
import com.spring.tutorials.cloudstreams.stream.Event;
import com.spring.tutorials.cloudstreams.stream.OrdersSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author Peter Oates
 */
@RestController
public class OrderController {

    @Autowired
    private OrderRepository orders;

    @Autowired
    private OrdersSource orderSource;

    @Value("${originator}")
    private String originator;

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Order> getOrder() {

        Iterable<Order> orderList = orders.findAll();

        return orderList;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Order> add(@RequestBody Order input) {

        orders.save(input);

        // place order on Kinesis Stream
        orderSource.sendOrder(new Event(input, "ORDER", originator));

        return new ResponseEntity<Order>(input, HttpStatus.OK);
    }

}