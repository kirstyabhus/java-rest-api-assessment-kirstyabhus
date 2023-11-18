package com.cbfacademy.apiassessment.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.InvestmentRepository;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.utility.JsonUtility;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;

@Service
public class InvestmentService {

    private String filePath = "C:///Users//kabhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//data.json";

    @Autowired
    private JsonUtility jsonUtility;

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
    public void createOrUpdateInvestment(UUID portfolioId, Investment investment) {
        investmentRepository.save(portfolioId, investment);
    }

    // delete an investment
    public void deleteInvestment(UUID portfolioId, UUID investmentId) {
        investmentRepository.deleteInvestment(portfolioId, investmentId);
    }

    // get sorted investments (by type, name, symbol, shareQuantity, purchasePrice,
    // totalValue or currentValue)
    public List<Investment> getSortedInvestments(UUID portfolioId, String sortCriteria, String sortOrder) {
        // Map<UUID, Investment> investmentMap =
        // investmentRepository.getInvestmentMap(portfolioId);
        Map<UUID, Investment> investmentMap = investmentRepository.getInvestmentMap();
        Map<UUID, Portfolio> portfoliosMap = investmentRepository.getPortfoliosMap();

        investmentRepository.populateInvestmentMap(portfolioId);

        // get the comparator -> sorting criteria
        Comparator<Investment> comparator = getComparatorForSortCriteria(sortCriteria);

        if (comparator == null) {
            throw new IllegalArgumentException("Invalid sort criteria: " + sortCriteria);
        }

        List<Investment> sortedInvestments = new ArrayList<>(investmentMap.values());

        // Sort based on ascending or descending order
        if (sortOrder.equalsIgnoreCase("asc")) {
            sortedInvestments.sort(comparator);
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            sortedInvestments.sort(comparator.reversed());
        } else {
            throw new IllegalArgumentException("Invalid sort order: " + sortOrder);
        }

        investmentRepository.updatePortfolio(portfolioId, sortedInvestments);

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
