package com.example.springboot.locale.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class PageController {

    @Autowired
    MessageSource messageSource;

    @GetMapping("/international")
    public String getInternationalPage(@RequestHeader(name="Accept-Language", required=false) Locale locale) {

        return messageSource.getMessage("greeting",null,locale);

    }
}