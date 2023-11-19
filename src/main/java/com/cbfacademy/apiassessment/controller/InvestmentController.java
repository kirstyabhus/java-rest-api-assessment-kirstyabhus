package com.cbfacademy.apiassessment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.service.InvestmentService;

/**
 * Controller handling investment-related operations.
 * Exposes endpoints for managing investments.
 */
@RestController
@RequestMapping("/api/v1/portfolios")
public class InvestmentController {

    // @Autowired
    private final InvestmentService service;

    // investment service constructor
    InvestmentController(InvestmentService service) {
        this.service = service;
    }

    // GET all investments
    @GetMapping("{portfolioId}/investments")
    public ResponseEntity<List<Investment>> getAllPortfolios(@PathVariable UUID portfolioId) {
        return ResponseEntity.ok(service.getAllInvestments(portfolioId));
    }

    // get investment by id
    @GetMapping("/{portfolioId}/investments/{investmentId}")
    public ResponseEntity<?> getInvestmentById(@PathVariable UUID portfolioId,
            @PathVariable UUID investmentId) {
        try {

            Investment investment = service.getInvestmentById(portfolioId, investmentId);

            if (investment != null) {
                return ResponseEntity.ok().body(investment);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Investment Not Found: Unable to retrieve investment");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to retrieve investment - " + e.getMessage());
        }
    }

    // create a new investment
    @PostMapping(path = "{portfolioId}/investments/new", produces = "application/json")
    public ResponseEntity<?> createInvestment(@PathVariable UUID portfolioId, @RequestBody Investment investment) {
        try {

            service.createOrUpdateInvestment(portfolioId, investment);

            String feedbackMessage = "Investment created successfully";
            return ResponseEntity.status(HttpStatus.CREATED).body(feedbackMessage);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to create investment - " + e.getMessage());
        }
    }

    // update an investment
    @PutMapping("/{portfolioId}/investments/{investmentId}")
    public ResponseEntity<?> updateInvestment(@PathVariable UUID portfolioId, @PathVariable UUID investmentId,
            @RequestBody Investment investment) {
        try {

            service.createOrUpdateInvestment(portfolioId, investment);

            String feedbackMessage = "Investment updated successfully";
            return ResponseEntity.ok().body(feedbackMessage);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to update investment - " + e.getMessage());
        }
    }

    // delete an investment
    @DeleteMapping("{portfolioId}/investments/{investmentId}")
    public ResponseEntity<?> deleteInvestment(@PathVariable UUID portfolioId, @PathVariable UUID investmentId) {
        try {

            service.deleteInvestment(portfolioId, investmentId);

            String feedbackMessage = "Investment deleted successfully";
            return ResponseEntity.ok().body(feedbackMessage);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to delete investment - " + e.getMessage());
        }
    }

    // get investments sorted (by type, name, symbol, shareQuantity, purchasePrice,
    // totalValue or currentValue)
    @GetMapping("{portfolioId}/investments/sorted")
    public ResponseEntity<?> getSortedInvestments(@PathVariable UUID portfolioId,
            @RequestParam(name = "sort_by", defaultValue = "name") String sortCriteria,
            @RequestParam(name = "order_by", defaultValue = "asc") String sortOrder) {
        try {

            List<Investment> sortedInvestments = service.getSortedInvestments(portfolioId, sortCriteria, sortOrder);

            return ResponseEntity.ok().body(sortedInvestments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to get sorted investments - " + e.getMessage());
        }
    }

    // filter investments by type
    @GetMapping("{portfolioId}/investments/filter-by-type")
    public ResponseEntity<?> getInvestmentsFilteredByType(@PathVariable UUID portfolioId,
            @RequestParam(name = "investment_type", defaultValue = "Stock") String investmentType) {
        try {

            List<Investment> filteredInvestments = service.filterInvestmentsByType(portfolioId, investmentType);

            return ResponseEntity.ok().body(filteredInvestments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to filter investments by type - " + e.getMessage());
        }
    }
}
