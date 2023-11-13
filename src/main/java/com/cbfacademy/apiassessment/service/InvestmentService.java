package com.cbfacademy.apiassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.InvestmentRepository;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository investmentRepository;

    // TODO BASIC CRUD
    // create investment
    // read investment
    // update investment
    // delete investment

    // TODO OTHER LOGIC
    // sort investments (in a specific portolfio!) by:
    // - value
    // - name
    // - symbol
    // - FUTURE IMPLEMENTATION -> ESG scores
    // filter investments (in a specific portolio)
    // -
}
