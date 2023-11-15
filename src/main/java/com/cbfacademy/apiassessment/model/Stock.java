package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Stock class is annotated with @Entity, indicating that it is a JPA entity 
// i.e it will be mapped to a table named Stock
@Entity
public class Stock {

    // id property is annotated with @Id so that JPA recognises it as the object's
    // ID
    // id will also be generated automatically with the @GeneratedValue annotation
    @Id
    private UUID id;
    // these other properties will be mapped to columns that share the same names as
    // them
    private String symbol;
    private String name;
    private Double stockValue;

    // default no-arg constructor for when GSON deser JSON into Stock object
    public Stock() {
        this.id = UUID.randomUUID();
    }

    // this constructor is to create instances of Stock
    public Stock(String symbol, String name, Double stockValue) {
        this.id = UUID.randomUUID();
        this.symbol = symbol;
        this.name = name;
        this.stockValue = stockValue;

    }

    // will print out the Stock's properties
    @Override
    public String toString() {
        return String.format("Stock[id=%s, symbol='%s', name='%s', stockValue='%.2f']", id, symbol, name, stockValue);
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

    public Double getStockValue() {
        return stockValue;
    }

    public void setStockValue(Double stockValue) {
        this.stockValue = stockValue;
    }

}
