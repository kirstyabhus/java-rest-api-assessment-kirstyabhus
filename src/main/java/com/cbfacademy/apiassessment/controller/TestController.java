package com.cbfacademy.apiassessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RestController
public class TestController {

    // @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "Humans.") String name) {
        return String.format("Hello %s", name);
    }
}
