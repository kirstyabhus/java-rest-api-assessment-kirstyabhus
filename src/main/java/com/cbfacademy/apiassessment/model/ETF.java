package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class ETF implements Investment {
    private UUID id;
    private String symbol;
    private String name;
    private Double value;

    private Portfolio portfolio; // ? do I need @ManyToOne annotation etc.?

    public ETF(UUID id, String symbol, String name, Double value, Portfolio portfolio) {
        this.id = UUID.randomUUID();
        this.symbol = symbol;
        this.name = name;
        this.value = value;
        this.portfolio = portfolio;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getValue() {
        return value;
    };

    public void setValue(Double value) {
        this.value = value;
    };

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPorfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

}
