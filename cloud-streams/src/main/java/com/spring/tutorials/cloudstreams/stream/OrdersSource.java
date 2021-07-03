package com.spring.tutorials.cloudstreams.stream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OrdersSource {

    private final Log logger = LogFactory.getLog(getClass());

    private BlockingQueue<Event> orderEvent = new LinkedBlockingQueue<>();

    @Bean
    public Supplier<Event> produceOrder() {
        return () -> this.orderEvent.poll();
    }

    public void sendOrder(Event event) {
        this.orderEvent.offer(event);
        logger.info("Event sent: " + event.toString());
    }
}