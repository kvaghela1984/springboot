package com.spring.tutorials.cloudstreams.stream;

import com.spring.tutorials.cloudstreams.repository.OrderRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderStreamConfiguration {

    private final Log logger = LogFactory.getLog(getClass());

    @Bean
    public Consumer<Event> processOrder(OrderRepository orders) {
        return event -> {
            //log the order received
            if (!event.getOriginator().equals("test-application")) {
                logger.info("An order has been received " + event.toString());
                orders.save(event.getSubject());
            } else {
                logger.info("An order has been placed from this service " + event.toString());
            }
        };
    }

}