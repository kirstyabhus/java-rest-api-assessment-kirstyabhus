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
import com.cbfacademy.apiassessment.utility.JsonUtility;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;

/**
 * Service class handling investment-related operations.
 */
@Service
public class InvestmentService {

    private String filePath = "C:///Users//kabhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//data.json";
    private static final Logger logger = LoggerFactory.getLogger(InvestmentService.class);

    @Autowired
    private JsonUtility jsonUtility;

    @Autowired
    private final InvestmentRepository investmentRepository;

    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    /**
     * Retrieves all investments associated with the specified portfolio ID.
     *
     * @param portfolioId The unique identifier of the portfolio for which
     *                    investments are retrieved.
     * @return A list of investments associated with the provided portfolio ID.
     * @throws RuntimeException if an error occurs while retrieving the investments.
     *                          The exception wraps the underlying cause.
     */
    public List<Investment> getAllInvesments(UUID portfolioId) {
        List<Investment> investments = new ArrayList<>();

        try {
            logger.info("Attempting to retrieve all investments for portfolio with ID: {}", portfolioId);
            investments = investmentRepository.findAll(portfolioId);
            logger.info("Retrieved {} investments for portfolio with ID: {}", investments.size(), portfolioId);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving investments for portfolio with ID {}: {}", portfolioId,
                    e.getMessage());
            throw new RuntimeException(
                    "Failed to retrieve investments for portfolio ID  " + portfolioId, e);
        }
        return investments;
    }

    /**
     * Retrieves an investment by its ID associated with the specified portfolio ID.
     *
     * @param portfolioId  The unique identifier of the portfolio containing the
     *                     investment.
     * @param investmentId The unique identifier of the investment to retrieve.
     * @return The investment associated with the provided investment ID and
     *         portfolio ID.
     *         Returns null if no investment is found with the given IDs.
     * @throws RuntimeException if an error occurs while retrieving the investment.
     *                          The exception wraps the underlying cause.
     */
    public Investment getInvestmentById(UUID portfolioId, UUID investmentId) {
        Investment investment = null;

        try {
            logger.info("Attempting to retrieve investment with ID {} from portfolio with ID {}", investmentId,
                    portfolioId);
            investment = investmentRepository.findById(portfolioId, investmentId);
            if (investment != null) {
                logger.info("Retrieved investment {} from portfolio with ID {}", investmentId, portfolioId);
            } else {
                logger.warn("No investment found with ID {} in portfolio with ID {}", investmentId, portfolioId);
            }
        } catch (Exception e) {
            logger.error("Error occurred while retrieving investment {} from portfolio {}: {}", investmentId,
                    portfolioId, e.getMessage());
            throw new RuntimeException(
                    String.format("Failed to retrieve investment %s from portfolio %s", investmentId, portfolioId), e);
        }

        return investment;
    }

    /**
     * Creates a new investment or updates an existing investment for the specified
     * portfolio.
     *
     * @param portfolioId The unique identifier of the portfolio.
     * @param investment  The investment to be created or updated.
     * @throws RuntimeException if an error occurs while saving the investment.
     *                          The exception wraps the underlying cause.
     */
    public void createOrUpdateInvestment(UUID portfolioId, Investment investment) {
        try {
            logger.info("Attempting to create/update investment for portfolio with ID: {}", portfolioId);
            investmentRepository.save(portfolioId, investment);
            logger.info("Successfully created or updated investment for portfolio with ID: {}", portfolioId);
        } catch (Exception e) {
            logger.error("Error occurred while creating/updating investment for portfolio with ID {}: {}",
                    portfolioId, e.getMessage());
            throw new RuntimeException(
                    "Failed to create/update investment for portfolio ID " + portfolioId, e);
        }
    }

    /**
     * Deletes an investment associated with the specified portfolio and investment
     * IDs.
     *
     * @param portfolioId  The unique identifier of the portfolio containing the
     *                     investment to be deleted.
     * @param investmentId The unique identifier of the investment to be deleted.
     * @throws RuntimeException if an error occurs while deleting the investment.
     *                          The exception wraps the underlying cause.
     */
    public void deleteInvestment(UUID portfolioId, UUID investmentId) {
        try {
            logger.info("Attempting to delete investment with ID {} from portfolio with ID {}", investmentId,
                    portfolioId);
            investmentRepository.deleteInvestment(portfolioId, investmentId);
            logger.info("Successfully deleted investment with ID {} from portfolio with ID {}", investmentId,
                    portfolioId);
        } catch (Exception e) {
            logger.error("Error occurred while deleting investment with ID {} from portfolio with ID {}: {}",
                    investmentId, portfolioId, e.getMessage());
            throw new RuntimeException(
                    "Failed to delete investment with ID " + investmentId + " from portfolio with ID " + portfolioId,
                    e);
        }
    }

    /**
     * Retrieves a sorted list of investments associated with the specified
     * portfolio ID
     * sorted based on the provided sorting criteria and order.
     *
     * @param portfolioId  The unique identifier of the portfolio containing the
     *                     investments to be sorted.
     * @param sortCriteria The criteria by which investments will be sorted (e.g.,
     *                     "type", "name", etc.).
     * @param sortOrder    The sorting order ("asc" for ascending, "desc" for
     *                     descending).
     * @return A sorted list of investments based on the given criteria and order.
     * @throws IllegalArgumentException if an invalid sort criteria or order is
     *                                  provided.
     * @throws RuntimeException         if an error occurs during the sorting
     *                                  process. The exception wraps the underlying
     *                                  cause.
     */
    public List<Investment> getSortedInvestments(UUID portfolioId, String sortCriteria, String sortOrder) {
        try {
            // get the portfolio and investments
            Map<UUID, Investment> investmentMap = investmentRepository.getInvestmentMap();
            Map<UUID, Portfolio> portfoliosMap = investmentRepository.getPortfoliosMap();

            investmentRepository.populateInvestmentMap(portfolioId);

            // get the comparator through provided sorting criteria
            Comparator<Investment> comparator = getComparatorForSortCriteria(sortCriteria);

            if (comparator == null) {
                throw new IllegalArgumentException("Invalid sort criteria: " + sortCriteria);
            }

            List<Investment> sortedInvestments = new ArrayList<>(investmentMap.values());

            // Sort based on ascending or descending order
            logger.info("Attempting to sort investments ");
            if (sortOrder.equalsIgnoreCase("asc")) {

                sortedInvestments.sort(comparator);

                logger.info("Sorted {} investments for portfolio ID: {} based on criteria: {} in ascending order",
                        sortedInvestments.size(), portfolioId, sortCriteria);
            } else if (sortOrder.equalsIgnoreCase("desc")) {

                sortedInvestments.sort(comparator.reversed());

                logger.info("Sorted {} investments for portfolio ID: {} based on criteria: {} in descending order",
                        sortedInvestments.size(), portfolioId, sortCriteria);
            } else {
                throw new IllegalArgumentException("Invalid sort order: " + sortOrder);
            }

            investmentRepository.updatePortfolio(portfolioId, sortedInvestments);

            // update JSON with sorted investments
            try {
                ArrayList<Portfolio> portfolioList = new ArrayList<>(portfoliosMap.values());
                jsonUtility.writePortfoliosToJSON(portfolioList, filePath);
            } catch (Exception e) {
                logger.error("Error occurred while sorting and updating JSON: {}", e.getMessage());
                throw new RuntimeException("Failed to sort investments and update JSON", e);
            }

            return sortedInvestments;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid sort criteria or order provided: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while sorting investments: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve and sort investments", e);
        }
    }

    /**
     * Determines the sort comparator based on the given sortCriteria.
     *
     * @param sortCriteria The criteria based on which investments are sorted.
     * @return The comparator based on the provided sortCriteria.
     * @throws IllegalArgumentException if an invalid sort criteria is provided.
     */
    private Comparator<Investment> getComparatorForSortCriteria(String sortCriteria) {
        try {
            switch (sortCriteria.toLowerCase()) {
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
                    throw new IllegalArgumentException("Invalid sort criteria: " + sortCriteria);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid sort criteria provided: {}", sortCriteria);
            throw e;
        } catch (Exception e) {
            logger.error("Error determining sort comparator for criteria: {}", sortCriteria, e);
            throw new RuntimeException("Error determining sort comparator for criteria: " + sortCriteria, e);
        }
    }

    /**
     * Filters investments based on the specified investment type for the given
     * portfolio.
     *
     * @param portfolioId    The unique identifier of the portfolio to filter
     *                       investments from.
     * @param investmentType The type of investment to filter by.
     * @return A list of investments filtered by the specified type.
     * @throws RuntimeException if an error occurs while filtering investments.
     *                          The exception wraps the underlying cause.
     */
    public List<Investment> filterInvestmentsByType(UUID portfolioId, String investmentType) {
        List<Investment> filteredInvestments = new ArrayList<>();

        try {
            logger.info("Filtering investments by type '{}' for portfolio ID: {}", investmentType, portfolioId);

            // Get all investments for the specified portfolio
            List<Investment> investments = investmentRepository.findAll(portfolioId);

            // Filter investments by investment type
            for (Investment investment : investments) {
                if (investment.getType().equalsIgnoreCase(investmentType)) {
                    filteredInvestments.add(investment);
                }
            }

            logger.info("Filtered {} investments by type '{}' for portfolio ID: {}", filteredInvestments.size(),
                    investmentType, portfolioId);
        } catch (Exception e) {
            logger.error("Error filtering investments by type for portfolio ID {}: {}", portfolioId, e.getMessage());
            throw new RuntimeException("Failed to filter investments by type for portfolio ID " + portfolioId, e);
        }

        return filteredInvestments;
    }

    // - FUTURE IMPLEMENTATION -> ESG scores
    // moe a investment between portfolios
}
