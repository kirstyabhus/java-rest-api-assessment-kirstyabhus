package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stock implements Investment {
    // private final UUID id;
    @Id
    private UUID id;
    private String type = "Stock";
    private String symbol;
    private String name;
    private int sharesQuantity;
    private double purchasePrice;
    private double totalValue;
    private double currentValue; // TODO Fetch current value from API?
    // TODO returns? totalValue? (sharesQuant * purchasePrice?)

    // private Double esgScore; // TODO

    // default no-arg constructor for when GSON deser JSON into Stock object
    public Stock() {
        this.id = UUID.randomUUID();
    }

    // constructor for when user is giving id (e.g. for PUT request, when id is
    // already given)
    public Stock(UUID id, String type, String symbol, String name, int sharesQuantity, double purchasePrice,
            double totalValue,
            double currentValue) {
        this.id = id;
        this.type = type;
        this.symbol = symbol;
        this.name = name;
        this.sharesQuantity = sharesQuantity;
        this.purchasePrice = purchasePrice;
        this.totalValue = totalValue;
        this.currentValue = currentValue;
    }

    public Stock(String type, String symbol, String name, int sharesQuantity, double purchasePrice, double totalValue,
            double currentValue) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.symbol = symbol;
        this.name = name;
        this.sharesQuantity = sharesQuantity;
        this.purchasePrice = purchasePrice;
        this.totalValue = totalValue;
        this.currentValue = currentValue;
    }
    /*
     * public Double getEsgScore() {
     * return esgScore;
     * }
     * 
     * public void setEsgScore(Double esgScore) {
     * this.esgScore = esgScore;
     * }
     */

    @Override
    public String toString() {
        return String.format(
                "Stock[id=%s, type=%s, symbol='%s', name='%s', sharesQuantity='%d', purchasePrice='%.2f', totalValue='%.2f', currentValue='%.2f']",
                id, type, symbol, name, sharesQuantity, purchasePrice, totalValue, currentValue);
    }

    public String getType() {
        return type;
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

    public int getSharesQuantity() {
        return sharesQuantity;
    }

    public void setSharesQuantity(int sharesQuantity) {
        this.sharesQuantity = sharesQuantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

}
