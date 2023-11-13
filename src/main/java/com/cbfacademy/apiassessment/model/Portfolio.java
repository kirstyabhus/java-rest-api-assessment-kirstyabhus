package com.cbfacademy.apiassessment.model;

import java.util.Map;
import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Portfolio {

    private UUID portfolioId;
    private String name;
    private Map<String, Investment> investments;

    public Portfolio(UUID id, String name, Map<String, Investment> investments) {
        this.portfolioId = UUID.randomUUID();
        this.name = name;
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

    public Map<String, Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(Map<String, Investment> investments) {
        this.investments = investments;
    }

}
