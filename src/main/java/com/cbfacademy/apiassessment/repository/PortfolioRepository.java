package com.cbfacademy.apiassessment.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.utility.JsonUtility;

import jakarta.annotation.PostConstruct;

@Repository
public class PortfolioRepository {

    private String filePath = "C:///Users//kabhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//data.json";

    // map to store our data from json in the form ID:Portfolio
    private final Map<UUID, Portfolio> portfoliosMap = new HashMap<>();

    @Autowired
    private JsonUtility jsonUtility;

    // populate the map with our portfolio data from the JSON file "storage"
    @PostConstruct
    public void populatePortfolioMap() {
        try {
            List<Portfolio> portfolioList = jsonUtility.readPortfoliosFromJSON(filePath);

            portfolioList.forEach(portfolio -> portfoliosMap.put(portfolio.getPortfolioId(), portfolio));
        } catch (IOException e) {
            System.out.println("Error populating portfolioMap: " + e.getMessage());
        }
    }

    // get all portfolios
    public List<Portfolio> findAll() {
        return new ArrayList<>(portfoliosMap.values());
    }

    // get portfolio by id
    public Portfolio findById(UUID id) {
        return portfoliosMap.get(id);
    }

    // update the totalValue of each investment (to make sure new investments have a
    // calulated total value)
    public List<Investment> updateInvestmentsValues(Portfolio portfolio) {
        List<Investment> investments = portfolio.getInvestments();

        for (Investment investment : investments) {
            investment.calculateTotalValue(); // Update totalValue for each investment
        }

        return investments; // Return the updated list of investments
    }

    // create new portfolio or update portfolio
    public Portfolio save(Portfolio portfolio) {

        // update the given portfolio, ensuring all investments have a calculated
        // totalValue
        List<Investment> updatedInvestments = new ArrayList<Investment>(updateInvestmentsValues(portfolio));
        portfolio.setInvestments(updatedInvestments);
        portfolio.calculateTotalValue();

        // add/update the portfolio in the map
        portfoliosMap.put(portfolio.getPortfolioId(), portfolio);

        // update the json file
        try {
            ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());

            jsonUtility.writePortfoliosToJSON(portfolioList, filePath);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error creating portfolio: " + e.getMessage());
        }
        return portfolio;
    }

    // delete portfolio
    public void deletePortfolio(UUID id) {
        // TODO what if portfolio id not present?
        portfoliosMap.remove(id);
        // update the json file
        try {
            ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());

            jsonUtility.writePortfoliosToJSON(portfolioList, filePath);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // TODO sort portfolio
    public List<Portfolio> sortPortfolios(String sortCriteria) {
        Comparator<Portfolio> comparator = getComparatorForSortCriteria(sortCriteria);

        if (comparator == null) {
            throw new IllegalArgumentException("Invalid sort criteria: " + sortCriteria);
        }

        List<Portfolio> sortedPortfolios = new ArrayList<>(portfoliosMap.values());
        sortedPortfolios.sort(comparator);

        // sort the JSON
        try {
            jsonUtility.writePortfoliosToJSON(sortedPortfolios, filePath);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return sortedPortfolios;
    }

    private Comparator<Portfolio> getComparatorForSortCriteria(String sortCriteria) {
        switch (sortCriteria.toLowerCase()) {
            case "value":
                return Comparator.comparing(Portfolio::getTotalValue);
            case "name":
                return Comparator.comparing(Portfolio::getName);
            default:
                // if invalid criteria
                return null;

        }
    }

    // TODO filter portfolio by Total Holdings / Portfolio Value

}
