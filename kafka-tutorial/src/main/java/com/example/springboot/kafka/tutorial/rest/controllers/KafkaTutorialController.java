package com.example.springboot.kafka.tutorial.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class KafkaTutorialController {

    Random rand = new Random();

    @Autowired
    KafkaTemplate kafkaTemplate;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity signUp(){
        Integer i = rand.nextInt(100);
        kafkaTemplate.send("test-topic",i.toString());
        return ResponseEntity.status(HttpStatus.OK).body("Calculating Square of " + i);
    }
}
