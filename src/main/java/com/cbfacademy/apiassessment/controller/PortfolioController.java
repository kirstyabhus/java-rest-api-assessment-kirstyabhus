package com.cbfacademy.apiassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.service.PortfolioService;

@RestController
public class PortfolioController {
    @Autowired
    private PortfolioService service;

    PortfolioController(PortfolioService service) {
        this.service = service;
    }

}
