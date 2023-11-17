package com.cbfacademy.apiassessment.controller;

import java.util.List;
import java.util.UUID;

import javax.sound.sampled.Port;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.service.PortfolioService;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    // @Autowired
    private final PortfolioService service;

    PortfolioController(PortfolioService service) {
        this.service = service;
    }

    // get all portfolios
    @GetMapping("")
    public List<Portfolio> getAllPortfolios() {
        return service.getPortfolios();
    }

    // get a portfolio by id
    @GetMapping("/portfolioId/{id}")
    public Portfolio getPortfolioById(@PathVariable UUID id) {
        return service.findPortfolioById(id);
    }

    // create a new portfolio
    @PostMapping(path = "/new", produces = "application/json")
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        return service.createOrUpdatePortfolio(portfolio);
    }

    // update a portfolio
    @PutMapping("/portfolioId/{id}")
    public Portfolio updatePortfolio(@PathVariable UUID id, @RequestBody Portfolio portfolio) {
        return service.createOrUpdatePortfolio(portfolio);
    }

    // delete a portfolio
    @DeleteMapping("/portfolioId/{id}")
    public void deletePortfolio(@PathVariable UUID id) {
        service.deletePortfolio(id);
    }

    // TODO sort portfolios
    /*
     * @GetMapping("/sorted")
     * public ResponseEntity<List<Portfolio>> getSortedPortfolios(@RequestParam(name
     * = "sort", defaultValue = "name")String sortCriteria){
     * 
     * <List>Portfolio sortedPortfolios = service.getSortedPortfolios(sortCriteria);
     * }
     */
}
