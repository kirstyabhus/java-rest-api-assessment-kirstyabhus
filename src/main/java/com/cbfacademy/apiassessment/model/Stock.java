package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Stock implements Investment {
    // private final UUID id;

    // TODO change to UUID
    private UUID id;
    private String symbol;
    private String name;

    public Stock(UUID id, String symbol, String name, double currentPrice) {
        this.id = UUID.randomUUID();
        this.symbol = symbol;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
