package com.cbfacademy.apiassessment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.InvestmentRepository;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;

@Service
public class InvestmentService {
    @Autowired
    private final InvestmentRepository investmentRepository;

    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    // get all investments
    public List<Investment> getAllInvesments(UUID portfolioId) {
        return investmentRepository.findAll(portfolioId);
    }

    // get investment by id
    public Investment getInvestmentById(UUID portfolioId, UUID investmentId) {
        return investmentRepository.findById(portfolioId, investmentId);
    }

    // create new investment or update old investment
    public Investment createOrUpdateInvestment(UUID portfolioId, Investment investment) {
        return investmentRepository.save(portfolioId, investment);
    }

    // delete an investment
    public void deleteInvestment(UUID portfolioId, UUID investmentId) {
        investmentRepository.deleteInvestment(portfolioId, investmentId);
    }

    // get sorted investments (by type, name, symbol, shareQuantity, purchasePrice,
    // totalValue or currentValue)
    public List<Investment> getSortedInvestments(UUID portfolioId, String sortCriteria) {
        return investmentRepository.sortInvestments(portfolioId, sortCriteria);
    }

    // }

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
    // moe a investment between portfolios
}
