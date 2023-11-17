package com.cbfacademy.apiassessment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.repository.InvestmentRepository;
import com.cbfacademy.apiassessment.service.PortfolioService;

@RestController
@RequestMapping("/api/portfolios")
public class InvestmentController {

    // TODO change this to investment service
    // @Autowired
    private final InvestmentRepository repository;

    // investment service constructor
    InvestmentController(InvestmentRepository repository) {
        this.repository = repository;
    }

    // GET all investments
    @GetMapping("{portfolioId}/investments")
    public List<Investment> getAllPortfolios(@PathVariable UUID portfolioId) {
        return repository.findAll(portfolioId);
    }

    // GET investment by id
    @GetMapping("{portfolioId}/investments/{investmentId}")
    public Investment getInvestmentById(@PathVariable UUID portfolioId, @PathVariable UUID investmentId) {
        return repository.findById(portfolioId, investmentId);
    }

    // POST add a new investment

    // PUT /{investmentId} update an investment

    // DELETE /{investmentId} delete and investment

}
