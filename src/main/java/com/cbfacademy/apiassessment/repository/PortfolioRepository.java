package com.cbfacademy.apiassessment.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.utility.JsonUtility;

import jakarta.annotation.PostConstruct;

/**
 * Repository for handling Portfolio-related operations.
 * <p>
 * This repository manages interactions with portfolios, including population
 * from a JSON file,
 * retrieval, updates, and deletions.
 * </p>
 */
@Repository
public class PortfolioRepository {

    private String filePath = "C:///Users//kabhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//data//portfolios.json";
    private static final Logger logger = LoggerFactory.getLogger(PortfolioRepository.class);

    // map to store our data from json in the form ID:Portfolio
    private final Map<UUID, Portfolio> portfoliosMap = new HashMap<>();

    @Autowired
    private JsonUtility jsonUtility;

    /**
     * Populates the portfolios map from a JSON file.
     *
     * <p>
     * This method reads portfolio data from a JSON file and populates the
     * portfolios map
     * using the portfolio IDs as keys.
     * </p>
     * <p>
     * This method is annotated with @PostConstruct to execute it after object
     * construction
     * and before the application is fully operational.
     * </p>
     *
     * @throws RuntimeException if an IO error occurs during JSON reading.
     */
    @PostConstruct
    public void populatePortfolioMap() {
        try {
            List<Portfolio> portfolioList = jsonUtility.readPortfoliosFromJSON(filePath);

            portfolioList.forEach(portfolio -> portfoliosMap.put(portfolio.getPortfolioId(), portfolio));

            logger.info("Portfolios map populated successfully.");
        } catch (IOException e) {
            logger.error("Error populating portfolios map: " + e.getMessage());
            throw new RuntimeException("Error populating portfolios map", e);
        }
    }

    /**
     * Retrieves the portfolios map.
     *
     * @return The map containing portfolios with their unique IDs as keys.
     */
    public Map<UUID, Portfolio> getPortfoliosMap() {
        return portfoliosMap;
    }

    /**
     * Retrieves all portfolios.
     *
     * @return A list containing all available portfolios.
     */
    public List<Portfolio> findAll() {
        try {
            return new ArrayList<>(portfoliosMap.values());
        } catch (Exception e) {
            logger.error("Error retrieving all portfolios: " + e.getMessage());
            throw new RuntimeException("Error retrieving all portfolios", e);
        }
    }

    /**
     * Retrieves a portfolio by its unique ID.
     *
     * @param id The unique identifier of the portfolio to retrieve.
     * @return The portfolio corresponding to the provided ID.
     * @throws RuntimeException if an error occurs while retrieving the portfolio by
     *                          ID.
     */
    public Portfolio findById(UUID id) {
        try {
            return portfoliosMap.get(id);
        } catch (Exception e) {
            logger.error("Error retrieving portfolio by ID: " + e.getMessage());
            throw new RuntimeException("Error retrieving portfolio by ID", e);
        }
    }

    /**
     * Updates the total value of each investment in a portfolio.
     *
     * @param portfolio The portfolio whose investments' total values need to be
     *                  updated.
     * @return The list of updated investments.
     * @throws RuntimeException if an error occurs while updating investments' total
     *                          values.
     */
    public List<Investment> updateInvestmentsValues(Portfolio portfolio) {
        try {
            List<Investment> investments = portfolio.getInvestments();

            for (Investment investment : investments) {
                investment.calculateTotalValue(); // Update totalValue for each investment
            }

            return investments; // Return the updated list of investments
        } catch (Exception e) {
            logger.error("Error updating investments' total values: " + e.getMessage());
            throw new RuntimeException("Error updating investments' total values", e);
        }
    }

    /**
     * Creates a new portfolio or updates an existing portfolio.
     *
     * @param portfolio The portfolio to create or update.
     * @return The created or updated portfolio.
     * @throws RuntimeException if an error occurs while creating or updating the
     *                          portfolio.
     */
    public Portfolio save(Portfolio portfolio) {
        try {
            // Update the given portfolio, ensuring all investments have a calculated
            // totalValue
            List<Investment> updatedInvestments = new ArrayList<>(updateInvestmentsValues(portfolio));
            portfolio.setInvestments(updatedInvestments);
            portfolio.calculateTotalValue();

            // Add/update the portfolio in the map
            portfoliosMap.put(portfolio.getPortfolioId(), portfolio);

            // Update the JSON file
            ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());
            jsonUtility.writePortfoliosToJSON(portfolioList, filePath);

            return portfolio;
        } catch (Exception e) {
            logger.error("Error creating or updating portfolio: " + e.getMessage());
            throw new RuntimeException("Error creating or updating portfolio", e);
        }
    }

    /**
     * Deletes a portfolio by its unique ID.
     *
     * @param id The unique identifier of the portfolio to delete.
     * @throws IllegalArgumentException if the provided ID is null.
     * @throws RuntimeException         if an error occurs while deleting the
     *                                  portfolio.
     */
    public void deletePortfolio(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        try {
            boolean removed = portfoliosMap.remove(id) != null;

            if (!removed) {
                throw new IllegalArgumentException("No portfolio found for ID: " + id);
            }

            // Update the JSON file
            ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());
            jsonUtility.writePortfoliosToJSON(portfolioList, filePath);
        } catch (Exception e) {
            logger.error("Error deleting portfolio: " + e.getMessage());
            throw new RuntimeException("Error deleting portfolio", e);
        }
    }

}
