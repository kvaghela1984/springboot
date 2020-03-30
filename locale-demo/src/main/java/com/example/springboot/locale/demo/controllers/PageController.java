package com.example.springboot.locale.demo.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class PageController {
    private static Logger logger = LogManager.getLogger(PageController.class);
    @Autowired
    MessageSource messageSource;

    @GetMapping("/international")
    public ResponseEntity<String> getInternationalPage(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
        logger.info("procesing completed");
        return ResponseEntity.status(200).body(messageSource.getMessage("greeting",null,locale));

    }
}