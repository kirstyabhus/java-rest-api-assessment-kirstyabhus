package com.cbfacademy.apiassessment.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    public Portfolio save(Portfolio portfolio) {
        // add the portfolio into the map
        portfoliosMap.put(portfolio.getPortfolioId(), portfolio);

        // update the json file
        try {
            ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());

            jsonUtility.writePortfoliosToJSON(portfolioList, filePath);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return portfolio;
    }

    // delete portfolio
    public void deletePortfolioById(UUID id) {
        portfoliosMap.remove(id);
        // update the json file
        try {
            ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());

            jsonUtility.writePortfoliosToJSON(portfolioList, filePath);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
