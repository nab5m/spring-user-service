package com.nab5m.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String getHome() {
        return "Hello World";
    }

    @GetMapping("/private")
    public String getPrivatePage() {
        return "you are authenticated";
    }
}