package com.example.springboot.kafka.tutorial;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KafkaTutorialApplication {
	public static Logger logger = LoggerFactory.getLogger(KafkaTutorialApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaTutorialApplication.class, args);
	}

	@KafkaListener(topics = "test-topic")
	public void listen(ConsumerRecord<?, ?> cr) throws Exception {

		double sqrt = Math.sqrt(Integer.valueOf((String) cr.value()));
		logger.info(cr.toString());
		logger.info("Square Root of {} is {}", cr.value(),sqrt);
	}

}
