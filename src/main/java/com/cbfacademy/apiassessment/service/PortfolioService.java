package com.cbfacademy.apiassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.InvestmentRepository;

@Service
public class PortfolioService {

    @Autowired
    private InvestmentRepository investmentRepository;
}
