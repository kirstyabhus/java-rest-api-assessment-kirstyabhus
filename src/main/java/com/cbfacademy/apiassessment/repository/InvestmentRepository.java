package com.cbfacademy.apiassessment.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.utility.JsonUtility;

import jakarta.annotation.PostConstruct;

@Repository
public class InvestmentRepository {
    // stroe the invesments in a MAP (just like i did with the portfolio ->
    // id:investment)
    // but the thing is, it should only be done for the portfolioid that we are
    // currently at
    // so somehow need to make sure we've only got the data for the current
    // portfolio id

    private String filePath = "C:///Users//kabhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//data.json";

    // map to store our all portfolios data from json in the form
    // portfolioID:Portfolio
    private final Map<UUID, Portfolio> portfoliosMap = new HashMap<>();

    // map to store investments for a specific portfolio in the form
    // InvestmentID:Investment
    private final Map<UUID, Investment> investmentMap = new HashMap<>();

    @Autowired
    private JsonUtility jsonUtility;

    @PostConstruct
    public void populatePortfolioMap() {
        try {
            List<Portfolio> portfolioList = jsonUtility.readPortfoliosFromJSON(filePath);

            portfolioList.forEach(portfolio -> portfoliosMap.put(portfolio.getPortfolioId(), portfolio));
        } catch (IOException e) {
            System.out.println("Error populating portfolioMap: " + e.getMessage());
        }
    }

    // call this in the beginning before every method to populate the investment map
    // with investments mapped by id
    public void populateInvestmentMap(UUID portfolioId) {
        try {
            Portfolio portfolio = portfoliosMap.get(portfolioId);
            // if (portfolio != null) {
            List<Investment> investments = portfolio.getInvestments();

            // Clear existing investments related to other portfolios
            investmentMap.clear();

            // Put only the investments from the specified portfolio into the investmentMap
            investments.forEach(investment -> investmentMap.put(investment.getId(), investment));
            // }
        } catch (Exception e) {
            System.out.println("Error populating investmentMap: " + e.getMessage());
        }

    }

    // TODO so how would I update the investments of the portfolio?
    // 1. make edit to the investments 2. replace/rewrite the investments part of
    // the portfolio it came from (SETINVESTMENTS() method)
    // 3. update the portoflio i nthe json
    // may need to use the SAVE method from portfolio repository?

    // TODO would this run after every function?
    public void updatePortfolio(UUID portfolioId, List<Investment> investments) {
        Portfolio portfolio = portfoliosMap.get(portfolioId);
        portfolio.setInvestments(investments);

        // replace the portfolio in the portfolio map, with the portfolio that has
        // updated investments
        portfoliosMap.put(portfolioId, portfolio);
    }

    // get all investments
    public List<Investment> findAll(UUID portfolioId) {
        populateInvestmentMap(portfolioId); // Populate the investmentMap for the given portfolio

        // Retrieve investments related to the specified portfolio from the
        // investmentMap
        return new ArrayList<>(investmentMap.values());
    }

    // get investment by id
    public Investment findById(UUID investmentId) {
        return investmentMap.get(investmentId);
    }

}
