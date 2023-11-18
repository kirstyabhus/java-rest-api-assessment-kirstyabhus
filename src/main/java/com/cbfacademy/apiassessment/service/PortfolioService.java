package com.cbfacademy.apiassessment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.model.Portfolio;

@Service
public class PortfolioService {
    @Autowired
    private final PortfolioRepository portfolioRepository;

    PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    // get all portfolios
    public List<Portfolio> getPortfolios() {
        return portfolioRepository.findAll();
    }

    // get portfolio by id
    public Portfolio getPortfolioById(UUID id) {
        return portfolioRepository.findById(id);
    }

    // create a new portfolio / update a portfolio
    public Portfolio createOrUpdatePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    // delete a portfolio
    public void deletePortfolio(UUID id) {
        portfolioRepository.deletePortfolio(id);
    }

    // get sorted portfolios (by name or value)
    public List<Portfolio> getSortedPortfolios(String sortCriteria) {
        return portfolioRepository.sortPortfolios(sortCriteria);
    }

    // TODO OTHER LOGIC
    // sort portolio by name (if there's more than one portfolio)
    // move a stock between portfolios
}
