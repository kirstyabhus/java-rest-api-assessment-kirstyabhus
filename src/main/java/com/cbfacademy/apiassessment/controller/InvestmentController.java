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

import com.cbfacademy.apiassessment.exception.InvestmentNotFoundException;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.service.InvestmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Get all investments for a portfolio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of investments retrieved", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Investments Not Found: Unable to retrieve investments", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("{portfolioId}/investments")
    public ResponseEntity<?> getAllInvestments(@PathVariable UUID portfolioId) {
        List<Investment> investments = service.getAllInvestments(portfolioId);

        if (investments.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Investment Not Found: Unable to retrieve investments for the specified portfolio");
        }

        return ResponseEntity.ok(investments);
    }

    @Operation(summary = "Get investment by ID for a portfolio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investment found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Investment.class))),
            @ApiResponse(responseCode = "404", description = "Investment Not Found: Unable to retrieve investment", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to retrieve investment", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{portfolioId}/investments/{investmentId}")
    public ResponseEntity<?> getInvestmentById(@PathVariable UUID portfolioId,
            @PathVariable UUID investmentId) {
        try {

            Investment investment = service.getInvestmentById(portfolioId, investmentId);

            return ResponseEntity.ok().body(investment);

        } catch (InvestmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Investment Not Found: Unable to retrieve investment");
        } catch (

        Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to retrieve investment - " + e.getMessage());
        }
    }

    @Operation(summary = "Create a new investment for a portfolio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Investment created successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to create investment", content = @Content(mediaType = "text/plain"))
    })
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

    @Operation(summary = "Update an existing investment for a portfolio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investment updated successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to update investment", content = @Content(mediaType = "text/plain"))
    })
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

    @Operation(summary = "Delete an investment for a portfolio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investment deleted successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to delete investment", content = @Content(mediaType = "text/plain"))
    })
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

    @Operation(summary = "Get investments sorted by specified criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sorted investments retrieved", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to get sorted investments", content = @Content(mediaType = "text/plain"))
    })
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

    @Operation(summary = "Filter investments by specified type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered investments retrieved", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to filter investments by type", content = @Content(mediaType = "text/plain"))
    })
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
