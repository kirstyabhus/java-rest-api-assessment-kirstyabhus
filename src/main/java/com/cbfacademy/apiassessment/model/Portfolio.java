package com.cbfacademy.apiassessment.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Portfolio {

    private UUID portfolioId;
    private String name;
    private List<Investment> investments; // check this why is it not an array

    public Portfolio() {

    }

    public Portfolio(String name, List<Investment> investments) {
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
