package com.cbfacademy.apiassessment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;

@Service
public class PortfolioService {
    @Autowired
    private final PortfolioRepository portfolioRepository;

    PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Portfolio createPortfolio(String name, List<Investment> investments) {
        Portfolio portfolio = new Portfolio();
        // portfolio.setPortfolioId();
        portfolio.setName(name);
        portfolio.setInvestments(investments);
        return portfolio;
    }

    public List<Portfolio> getPortfolios() {
        return portfolioRepository.findAll();
    }

    // public Portfolio getPortfolioById(UUID portfolioId) {
    // return portfolioRepository.findById(portfolioId).orElse(null);
    // }

    // TODO BASIC CRUD
    // create portfolio
    // read portolio
    // update portfolio
    // delete portfolio

    // TODO OTHER LOGIC
    // sort portolio by name (if there's more than one portfolio)

}
