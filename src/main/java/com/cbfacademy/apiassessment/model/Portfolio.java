package com.cbfacademy.apiassessment.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;

@Entity
public class Portfolio {

    private UUID portfolioId;
    private String name;
    private double totalValue;
    private List<Investment> investments; // check this why is it not an array

    // default no-arg constructor for when GSON deser JSON into Portfolio object
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

    // calculates the total value of all investments in the portfolio
    public void calculateTotalValue() {
        this.totalValue = investments.stream()
                .mapToDouble(Investment::getPurchasePrice)
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
