package com.cbfacademy.apiassessment.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;

/**
 * Represents a portfolio containing a list of investments.
 * Annotated as an Entity.
 */
@Entity
public class Portfolio {

    private UUID portfolioId;
    private String name;
    private double totalValue;
    private List<Investment> investments;

    /**
     * Default constructor used by JSON deserialization.
     * Sets a provided UUID for the Portfolio.
     */
    @JsonCreator
    public Portfolio(
            @JsonProperty("portfolioId") UUID portfolioId,
            @JsonProperty("name") String name,
            @JsonProperty("totalValue") double totalValue,
            @JsonProperty("investments") List<Investment> investments) {
        this.portfolioId = portfolioId != null ? portfolioId : UUID.randomUUID();
        this.name = name;
        this.totalValue = totalValue;
        this.investments = investments;
    }

    /**
     * Constructs a Portfolio object with a randomly generated UUID.
     * Used when the UUID is not provided explicitly.
     */
    public Portfolio(String name, double totalValue, List<Investment> investments) {
        this.portfolioId = UUID.randomUUID();
        this.name = name;
        this.totalValue = totalValue;
        this.investments = investments;
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalValue() {
        return totalValue;
    }

    /**
     * Calculates the total value of all investments in the portfolio.
     */
    public void calculateTotalValue() {
        this.totalValue = investments.stream()
                .mapToDouble(Investment::getTotalValue)
                .sum();

    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId='" + portfolioId + '\'' +
                ", name='" + name + '\'' +
                ", investments=" + investments +
                '}';
    }

}
