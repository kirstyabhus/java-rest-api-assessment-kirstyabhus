package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ETF implements Investment {
    private UUID id;
    private String symbol;
    private String name;

    @ManyToOne
    @JoinColumn(name = "potfolio_id")
    private Portfolio portfolio; // ?

    public ETF(UUID id, String symbol, String name, Portfolio portfolio) {
        this.id = UUID.randomUUID();
        this.symbol = symbol;
        this.name = name;
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

    public Portfolio getPortfolio() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
