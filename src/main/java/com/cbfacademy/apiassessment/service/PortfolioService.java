package com.cbfacademy.apiassessment.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.InvestmentRepository;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.utility.JsonUtility;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;

/**
 * Service class handling portfolio-related operations.
 */
@Service
public class PortfolioService {

    private String filePath = "C:///Users//kabhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//data.json";
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    @Autowired
    private JsonUtility jsonUtility;

    @Autowired
    private final PortfolioRepository portfolioRepository;

    @Autowired
    private final InvestmentRepository investmentRepository;

    /**
     * Constructs a PortfolioService instance with the provided repositories.
     *
     * @param portfolioRepository  The repository handling portfolios.
     * @param investmentRepository The repository handling investments.
     */
    PortfolioService(PortfolioRepository portfolioRepository, InvestmentRepository investmentRepository) {
        this.portfolioRepository = portfolioRepository;
        this.investmentRepository = investmentRepository;
    }

    /**
     * Retrieves all portfolios.
     *
     * @return A list of all available portfolios.
     * @throws RuntimeException if an error occurs while retrieving portfolios.
     *                          The exception wraps the underlying cause.
     */
    public List<Portfolio> getPortfolios() {
        try {
            logger.info("Retrieving all portfolios");
            return portfolioRepository.findAll();
        } catch (Exception e) {
            logger.error("Error occurred while retrieving portfolios: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve portfolios", e);
        }
    }

    /**
     * Retrieves a portfolio by its ID.
     *
     * @param id The unique identifier of the portfolio to retrieve.
     * @return The portfolio corresponding to the provided ID.
     * @throws RuntimeException if an error occurs while retrieving the portfolio by
     *                          ID.
     *                          The exception wraps the underlying cause.
     */
    public Portfolio getPortfolioById(UUID id) {
        try {
            logger.info("Retrieving portfolio by ID: {}", id);
            return portfolioRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving portfolio by ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to retrieve portfolio by ID " + id, e);
        }
    }

    /**
     * Creates a new portfolio or updates an existing one.
     *
     * @param portfolio The portfolio object to be created or updated.
     * @return The created or updated portfolio.
     * @throws RuntimeException if an error occurs while creating or updating the
     *                          portfolio.
     *                          The exception wraps the underlying cause.
     */
    public Portfolio createOrUpdatePortfolio(Portfolio portfolio) {
        try {
            logger.info("Creating or updating portfolio: {}", portfolio);
            return portfolioRepository.save(portfolio);
        } catch (Exception e) {
            logger.error("Error occurred while creating or updating portfolio: {}", e.getMessage());
            throw new RuntimeException("Failed to create or update portfolio", e);
        }
    }

    /**
     * Deletes a portfolio by its ID.
     *
     * @param id The unique identifier of the portfolio to delete.
     * @throws RuntimeException if an error occurs while deleting the portfolio.
     *                          The exception wraps the underlying cause.
     */
    public void deletePortfolio(UUID id) {
        try {
            logger.info("Deleting portfolio by ID: {}", id);
            portfolioRepository.deletePortfolio(id);
        } catch (Exception e) {
            logger.error("Error occurred while deleting portfolio by ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete portfolio by ID " + id, e);
        }
    }

    /**
     * Retrieves sorted portfolios based on the specified criteria and order.
     *
     * @param sortCriteria The criteria for sorting portfolios (e.g., "value",
     *                     "name").
     * @param sortOrder    The order of sorting ("asc" for ascending, "desc" for
     *                     descending).
     * @return A list of sorted portfolios based on the given criteria and order.
     * @throws IllegalArgumentException if the provided sort criteria or order is
     *                                  invalid.
     * @throws RuntimeException         if an error occurs while sorting portfolios
     *                                  or updating the JSON file.
     *                                  The exception wraps the underlying cause.
     */
    public List<Portfolio> getSortedPortfolios(String sortCriteria, String sortOrder) {
        try {

            Map<UUID, Portfolio> portfoliosMap = portfolioRepository.getPortfoliosMap();

            Comparator<Portfolio> comparator = getComparatorForSortCriteria(sortCriteria);

            if (comparator == null) {
                throw new IllegalArgumentException("Invalid sort criteria: " + sortCriteria);
            }

            List<Portfolio> sortedPortfolios = new ArrayList<>(portfoliosMap.values());

            // Sort based on ascending or descending order
            logger.info("Attempting to sort portfolios ");
            if (sortOrder.equalsIgnoreCase("asc")) {
                sortedPortfolios.sort(comparator);
            } else if (sortOrder.equalsIgnoreCase("desc")) {
                sortedPortfolios.sort(comparator.reversed());
            } else {
                throw new IllegalArgumentException("Invalid sort order: " + sortOrder);
            }

            return sortedPortfolios;
        } catch (Exception e) {
            logger.error("Error occurred while sorting portfolios: {}", e.getMessage());
            throw new RuntimeException("Failed to get sorted portfolios", e);
        }
    }

    /**
     * Returns the comparator for sorting portfolios based on the provided criteria.
     *
     * @param sortCriteria The criteria for sorting portfolios (e.g., "value",
     *                     "name").
     * @return A comparator for sorting portfolios based on the given criteria.
     * @throws IllegalArgumentException if the provided sort criteria is invalid.
     */
    private Comparator<Portfolio> getComparatorForSortCriteria(String sortCriteria) {
        try {
            switch (sortCriteria.toLowerCase()) {
                case "value":
                    return Comparator.comparing(Portfolio::getTotalValue);
                case "name":
                    return Comparator.comparing(Portfolio::getName);
                default:
                    throw new IllegalArgumentException("Invalid sort criteria: " + sortCriteria);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid sort criteria provided: {}", sortCriteria);
            throw e;
        }
    }

    /**
     * Moves an investment from one portfolio to another.
     *
     * @param fromPortfolioId The unique identifier of the source portfolio.
     * @param toPortfolioId   The unique identifier of the destination portfolio.
     * @param investmentId    The unique identifier of the investment to move.
     * @return The list of all portfolios after the investment has been moved.
     * @throws IllegalArgumentException if the provided portfolio or investment ID
     *                                  is invalid.
     * @throws RuntimeException         if an error occurs while moving the
     *                                  investment.
     */
    public List<Portfolio> moveInvestment(UUID fromPortfolioId, UUID toPortfolioId, UUID investmentId) {
        try {
            // get the portfolios and investment by their id
            Portfolio fromPortfolio = portfolioRepository.getPortfoliosMap().get(fromPortfolioId);
            Portfolio toPortfolio = portfolioRepository.getPortfoliosMap().get(toPortfolioId);
            Investment investmentToMove = investmentRepository.findById(fromPortfolioId, investmentId);

            // Check if portfolios and investment exist
            if (fromPortfolio == null || toPortfolio == null || investmentToMove == null) {
                throw new IllegalArgumentException("Invalid portfolio or investment ID.");
            }

            logger.info("Attempting to move investment between portfolios.");
            // delete the investment from the source portfolio (also updates the JSON)
            investmentRepository.deleteInvestment(fromPortfolioId, investmentId);

            // add the investment to the destination portfolio (also updates the JSON)
            investmentRepository.save(toPortfolioId, investmentToMove);

            logger.info("Investment moved from Portfolio with ID {} to Portfolio with ID {}", fromPortfolioId,
                    toPortfolioId);

            return portfolioRepository.findAll();
        } catch (IllegalArgumentException e) {
            logger.error(String.format(
                    "Error moving investment with ID %s from portfolio with ID %s to portfolio with id %s: %s",
                    investmentId, fromPortfolioId, toPortfolioId, e.getMessage()));
            throw new IllegalArgumentException("Error moving investment: " + e.getMessage());
        } catch (Exception e) {
            logger.error(String.format(
                    "Error moving investment with ID %s from portfolio with ID %s to portfolio with id %s: %s",
                    investmentId, fromPortfolioId, toPortfolioId, e.getMessage()));
            throw new RuntimeException("Error moving investment", e);
        }
    }
}
