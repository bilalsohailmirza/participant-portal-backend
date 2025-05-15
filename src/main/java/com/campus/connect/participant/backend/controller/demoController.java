package com.campus.connect.participant.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class demoController {
    
    @GetMapping("/")
    public String root() {
        return new String("hello world");
    }
    
}
