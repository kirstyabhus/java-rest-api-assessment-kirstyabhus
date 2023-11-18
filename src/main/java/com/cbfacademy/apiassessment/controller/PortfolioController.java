package com.cbfacademy.apiassessment.controller;

import java.util.List;
import java.util.UUID;

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
@RequestMapping("/api/v1/portfolios")
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
    @GetMapping("/{id}")
    public Portfolio getPortfolioById(@PathVariable UUID id) {
        return service.getPortfolioById(id);
    }

    // create a new portfolio
    @PostMapping(path = "/new", produces = "application/json")
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        return service.createOrUpdatePortfolio(portfolio);
    }

    // update a portfolio
    @PutMapping("/{id}")
    public Portfolio updatePortfolio(@PathVariable UUID id, @RequestBody Portfolio portfolio) {
        return service.createOrUpdatePortfolio(portfolio);
    }

    // delete a portfolio
    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable UUID id) {
        service.deletePortfolio(id);
    }

    // get portfolios sorted by name or value
    @GetMapping("/sorted")
    public List<Portfolio> getSortedPortfolios(
            @RequestParam(name = "sort", defaultValue = "name") String sortCriteria,
            @RequestParam(name = "order", defaultValue = "asc") String sortOrder) {
        return service.getSortedPortfolios(sortCriteria, sortOrder);
        // should make a new list for the sorted
        // then return responsentit.ok(the list)
    }

    // move investment between portfolios
    @PostMapping(path = "/move-investment", produces = "application/json")
    public void moveInvestment(@RequestParam("fromPortfolioId") UUID fromPortfolioId,
            @RequestParam("toPortfolioId") UUID toPortfolioId,
            @RequestParam("investmentId") UUID investmentId) {
        service.moveInvestment(fromPortfolioId, toPortfolioId, investmentId);
    }

}
