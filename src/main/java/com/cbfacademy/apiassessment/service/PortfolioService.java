package com.cbfacademy.apiassessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.model.Portfolio;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Autowired
    PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    // TODO BASIC CRUD
    // create portfolio
    // read portolio
    // update portfolio
    // delete portfolio

    // TODO OTHER LOGIC
    // sort portolio by name (if there's more than one portfolio)

}
