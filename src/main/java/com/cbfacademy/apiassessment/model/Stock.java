package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Stock implements Investment {
    // private final UUID id;
    private UUID id;
    private String symbol;
    private String name;
    private Double value;
    private Portfolio portfolio;// ?

    public Stock(UUID id, String symbol, String name, double value) { // TODO does portfolio need to be in Constructor??
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

    public Double getValue() {
        return value;
    };

    public void setValue(Double value) {
        this.value = value;
    };
}
