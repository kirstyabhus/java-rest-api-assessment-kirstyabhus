package com.cbfacademy.apiassessment.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.utility.JsonUtility;

import jakarta.annotation.PostConstruct;

@Repository
public class InvestmentRepository {

    private String filePath = "C:///Users//kabhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//data.json";

    // store our all portfolios data from json in the form portfolioID:Portfolio
    private final Map<UUID, Portfolio> portfoliosMap = new HashMap<>();

    // store investments for a specific portfolio in the form
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

    // call at the beginning of every method, to populate the investment map
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

    // run when the portfolio map needs to be updated
    public void updatePortfolio(UUID portfolioId, List<Investment> investments) {
        Portfolio portfolio = portfoliosMap.get(portfolioId);
        portfolio.setInvestments(investments);

        // update totalVale of portfolio
        portfolio.calculateTotalValue();

        // replace the portfolio in the portfolio map, with the portfolio that has
        // updated investments
        portfoliosMap.put(portfolioId, portfolio);
    }

    // get all investments
    public List<Investment> findAll(UUID portfolioId) {
        populateInvestmentMap(portfolioId); // Populate the investmentMap for the given portfolio

        // Retrieve investments of the specific portfolio from the investmentMap
        return new ArrayList<>(investmentMap.values());
    }

    // get investment by id
    public Investment findById(UUID portfolioId, UUID investmentId) {
        populateInvestmentMap(portfolioId);

        return investmentMap.get(investmentId);
    }

    // create new investment or update the investment
    public Investment save(UUID portfolioId, Investment investment) {
        populateInvestmentMap(portfolioId);

        // update the totalValue of the investment
        investment.calculateTotalValue();

        // put (add or update) the investment into the map
        investmentMap.put(investment.getId(), investment);

        // add all the investments into a list
        ArrayList<Investment> investments = new ArrayList<>(investmentMap.values());
        updatePortfolio(portfolioId, investments);

        // update the json file
        try {
            ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());

            jsonUtility.writePortfoliosToJSON(portfolioList, filePath);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error updating investment list: " + e.getMessage());
        }
        return investment;
    }

    // delete an investment
    public void deleteInvestment(UUID portfolioId, UUID investmentId) {
        populateInvestmentMap(portfolioId);

        investmentMap.remove(investmentId);

        ArrayList<Investment> investments = new ArrayList<>(investmentMap.values());
        updatePortfolio(portfolioId, investments);

        // update the json file
        try {
            ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());

            jsonUtility.writePortfoliosToJSON(portfolioList, filePath);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error deleting investment: " + e.getMessage());
        }
    }

    // SORT INVESTMENTS
    public List<Investment> sortInvestments(UUID portfolioId, String sortCriteria) {

        populateInvestmentMap(portfolioId);

        // get the comparator -> sorting criteria
        Comparator<Investment> comparator = getComparatorForSortCriteria(sortCriteria);

        if (comparator == null) {
            throw new IllegalArgumentException("Invalid sort criteria: " + sortCriteria);
        }

        List<Investment> sortedInvestments = new ArrayList<>(investmentMap.values());
        sortedInvestments.sort(comparator);

        updatePortfolio(portfolioId, sortedInvestments);

        // sort the JSON
        try {
            ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());

            jsonUtility.writePortfoliosToJSON(portfolioList, filePath);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return sortedInvestments;
    }

    // determine the sort comparator based on the given sortCriteria
    private Comparator<Investment> getComparatorForSortCriteria(String sortCriteria) {
        switch (sortCriteria.toLowerCase()) {
            case "value":
                return Comparator.comparing(Investment::getTotalValue);
            case "name":
                return Comparator.comparing(Investment::getName);
            case "type":
                return Comparator.comparing(Investment::getType);
            case "symbol":
                return Comparator.comparing(Investment::getSymbol);
            case "shares-quantity":
                return Comparator.comparing(Investment::getSharesQuantity);
            case "purchase-price":
                return Comparator.comparing(Investment::getPurchasePrice);
            case "total-value":
                return Comparator.comparing(Investment::getTotalValue);
            case "current-value":
                return Comparator.comparing(Investment::getCurrentValue);
            default:
                // if invalid criteria
                return null;

        }
    }

}
