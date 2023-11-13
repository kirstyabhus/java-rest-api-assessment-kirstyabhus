package com.cbfacademy.apiassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.InvestmentRepository;

@Service
public class PortfolioService {

    @Autowired
    private InvestmentRepository investmentRepository;

    // TODO BASIC CRUD
    // create portfolio
    // read portolio
    // update portfolio
    // delete portfolio

    // TODO OTHER LOGIC
    // sort portolio by name (if there's more than one portfolio)

}
