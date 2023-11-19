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

import com.cbfacademy.apiassessment.exception.InvestmentNotFoundException;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.utility.JsonUtility;

import jakarta.annotation.PostConstruct;

/**
 * Repository for managing Investment-related operations.
 * <p>
 * This repository handles interactions with investments,
 * including populating portfolios and investment maps from JSON,
 * as well as specific investment operations.
 * </p>
 */
@Repository
public class InvestmentRepository {

    private String filePath = "C:///Users//abhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//data//portfolios.json";
    private static final Logger logger = LoggerFactory.getLogger(InvestmentRepository.class);

    // store our all portfolios data from json in the form portfolioID:Portfolio
    private final Map<UUID, Portfolio> portfoliosMap = new HashMap<>();

    // store investments for a specific portfolio in the form
    // InvestmentID:Investment
    private final Map<UUID, Investment> investmentMap = new HashMap<>();

    @Autowired
    private JsonUtility jsonUtility;

    /**
     * Populates the portfolio map from a JSON file.
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
     * @throws RuntimeException if an error occurs during the population of the
     *                          portfolio map.
     */
    @PostConstruct
    public void populatePortfolioMap() {
        try {
            List<Portfolio> portfolioList = jsonUtility.readPortfoliosFromJSON(filePath);

            portfolioList.forEach(portfolio -> portfoliosMap.put(portfolio.getPortfolioId(), portfolio));
        } catch (IOException e) {
            logger.error("Error populating portfolioMap: {}", e.getMessage(), e);
            throw new RuntimeException("Error populating portfolioMap", e);
        }
    }

    /**
     * Populates the investment map for a specific portfolio.
     *
     * @param portfolioId The unique identifier of the portfolio for which to
     *                    populate the investment map.
     * @throws IllegalArgumentException if the provided portfolioId is null.
     * @throws RuntimeException         if an error occurs during the population of
     *                                  the investment map.
     */
    public void populateInvestmentMap(UUID portfolioId) {
        if (portfolioId == null) {
            throw new IllegalArgumentException("Portfolio ID cannot be null");
        }

        try {
            Portfolio portfolio = portfoliosMap.get(portfolioId);
            if (portfolio != null) {
                List<Investment> investments = portfolio.getInvestments();

                // Clear existing investments related to other portfolios
                investmentMap.clear();

                // Put only the investments from the specified portfolio into the investmentMap
                investments.forEach(investment -> investmentMap.put(investment.getId(), investment));
            }
        } catch (Exception e) {
            logger.error("Error populating investmentMap: {}", e.getMessage(), e);
            throw new RuntimeException("Error populating investmentMap", e);
        }
    }

    /**
     * Retrieves the investment map for service.
     *
     * @return The investment map.
     */
    public Map<UUID, Investment> getInvestmentMap() {
        return investmentMap;
    }

    /**
     * Retrieves the portfolio map for service.
     *
     * @return The portfolio map.
     */
    public Map<UUID, Portfolio> getPortfoliosMap() {
        return portfoliosMap;
    }

    /**
     * Retrieves all investments for a given portfolio.
     *
     * @param portfolioId The unique identifier of the portfolio to retrieve
     *                    investments.
     * @return List of all investments for the specified portfolio.
     * @throws IllegalArgumentException if the portfolio ID is null.
     */
    public List<Investment> findAll(UUID portfolioId) {
        if (portfolioId == null) {
            logger.error("Portfolio ID cannot be null");
            throw new IllegalArgumentException("Portfolio ID cannot be null");
        }

        try {
            populateInvestmentMap(portfolioId); // Populate the investmentMap for the given portfolio
            return new ArrayList<>(investmentMap.values());
        } catch (Exception e) {
            logger.error("Error retrieving all investments: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving all investments", e);
        }
    }

    /**
     * Retrieves an investment by its ID for a specific portfolio.
     *
     * @param portfolioId  The unique identifier of the portfolio containing the
     *                     investment.
     * @param investmentId The unique identifier of the investment to retrieve.
     * @return The investment corresponding to the provided ID.
     * @throws IllegalArgumentException if the portfolio ID or investment ID is
     *                                  null.
     */
    public Investment findById(UUID portfolioId, UUID investmentId) {
        if (portfolioId == null || investmentId == null) {
            logger.error("Portfolio ID or Investment ID cannot be null");
            throw new IllegalArgumentException("Portfolio ID or Investment ID cannot be null");
        }

        try {
            populateInvestmentMap(portfolioId);
            return investmentMap.get(investmentId);
        } catch (Exception e) {
            logger.error("Error retrieving investment by ID: {}", e.getMessage(), e);
            throw new InvestmentNotFoundException("Investment with ID " + investmentId + " not found");
        }
    }

    /**
     * Creates a new investment or updates an existing one for a specific portfolio.
     *
     * @param portfolioId The unique identifier of the portfolio where the
     *                    investment belongs.
     * @param investment  The investment to save or update.
     * @throws IllegalArgumentException if the portfolio ID is null.
     * @throws RuntimeException         if an error occurs during the creation or
     *                                  update of the investment.
     */
    public void save(UUID portfolioId, Investment investment) {
        if (portfolioId == null) {
            logger.error("Portfolio ID cannot be null");
            throw new IllegalArgumentException("Portfolio ID cannot be null");
        }

        try {
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
                logger.error("Error updating investment list: {}", e.getMessage(), e);
                throw new RuntimeException("Error updating investment list", e);
            }
        } catch (Exception e) {
            logger.error("Error saving investment: {}", e.getMessage(), e);
            throw new RuntimeException("Error saving investment", e);
        }
    }

    /**
     * Updates the portfolio with new investments.
     *
     * @param portfolioId The unique identifier of the portfolio to update.
     * @param investments The list of investments to update.
     * @throws IllegalArgumentException if the portfolio ID is null or if no
     *                                  portfolio exists for the given ID.
     * @throws RuntimeException         if an error occurs during portfolio update.
     */
    public void updatePortfolio(UUID portfolioId, List<Investment> investments) {
        if (portfolioId == null) {
            logger.error("Portfolio ID cannot be null");
            throw new IllegalArgumentException("Portfolio ID cannot be null");
        }

        Portfolio portfolio = portfoliosMap.get(portfolioId);
        if (portfolio == null) {
            logger.error("No portfolio found for ID: {}", portfolioId);
            throw new IllegalArgumentException("No portfolio found for ID: " + portfolioId);
        }

        try {
            portfolio.setInvestments(investments);

            // update total value of portfolio
            portfolio.calculateTotalValue();

            // replace the portfolio in the portfolio map with the updated investments
            portfoliosMap.put(portfolioId, portfolio);
        } catch (Exception e) {
            logger.error("Error updating portfolio: {}", e.getMessage(), e);
            throw new RuntimeException("Error updating portfolio", e);
        }
    }

    /**
     * Deletes an investment from a specified portfolio.
     *
     * @param portfolioId  The unique identifier of the portfolio containing the
     *                     investment.
     * @param investmentId The unique identifier of the investment to delete.
     * @throws IllegalArgumentException if the portfolio ID or investment ID is
     *                                  null.
     * @throws RuntimeException         if an error occurs during the deletion of
     *                                  the investment.
     */
    public void deleteInvestment(UUID portfolioId, UUID investmentId) {
        if (portfolioId == null || investmentId == null) {
            logger.error("Portfolio ID or Investment ID cannot be null");
            throw new IllegalArgumentException("Portfolio ID or Investment ID cannot be null");
        }

        try {
            populateInvestmentMap(portfolioId);

            investmentMap.remove(investmentId);

            ArrayList<Investment> investments = new ArrayList<>(investmentMap.values());
            updatePortfolio(portfolioId, investments);

            // update the json file
            try {
                ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());
                jsonUtility.writePortfoliosToJSON(portfolioList, filePath);
            } catch (Exception e) {
                logger.error("Error deleting investment: {}", e.getMessage(), e);
                throw new RuntimeException("Error deleting investment", e);
            }
        } catch (Exception e) {
            logger.error("Error deleting investment: {}", e.getMessage(), e);
            throw new RuntimeException("Error deleting investment", e);
        }
    }

}
