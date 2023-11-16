package com.cbfacademy.apiassessment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // get all portfolios
    @GetMapping("")
    public List<Portfolio> getAllPortfolios() {
        return repository.findAll();
    }

    // get portfolio by id
    @GetMapping("/id/{id}")
    public Portfolio getPortfolioById(@PathVariable UUID id) {
        return repository.findById(id);
    }

    // create a new portfolio
    @PostMapping(path = "/new", produces = "application/json")
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        return repository.save(portfolio);
    }

    // delete a portfolio
    @DeleteMapping("/id/{id}")
    public void deletePortfolio(@PathVariable UUID id) {
        repository.deletePortfolio(id);
    }

    // update a portfolio
    @PutMapping("/id/{id}")
    public Portfolio updatePortfolio(@PathVariable UUID id, @RequestBody Portfolio portfolio) {
        return repository.save(portfolio);
    }
}
