package com.cbfacademy.apiassessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.service.PortfolioService;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    // @Autowired
    private final PortfolioRepository repository;

    PortfolioController(PortfolioRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<Portfolio> getAllPortfolios() {
        return repository.findAll();
    }

}
