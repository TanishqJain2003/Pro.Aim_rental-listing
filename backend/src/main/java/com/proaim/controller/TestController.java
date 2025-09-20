package com.proaim.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello! The application is running successfully!";
    }

    @GetMapping("/status")
    public String status() {
        return "Application Status: RUNNING";
    }
}
