package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class ETF implements Investment {
    private UUID id;
    private String symbol;
    private String name;

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

}
