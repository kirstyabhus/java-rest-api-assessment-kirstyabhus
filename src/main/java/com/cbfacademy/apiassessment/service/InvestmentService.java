package com.cbfacademy.apiassessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.InvestmentRepository;
import com.cbfacademy.apiassessment.model.Investment;

@Service
public class InvestmentService {

    private InvestmentRepository investmentRepository;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    // this will just get all invesments?
    public List<Investment> getAllInvesments() {
        return investmentRepository.findAll();
    }

    // get investments by portfolio?

    // findbyid
    // findbyname
    // findbysymbol

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
