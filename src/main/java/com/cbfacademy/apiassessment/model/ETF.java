package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class ETF implements Investment {
    private UUID id;
    private String type = "ETF";
    private String symbol;
    private String name;
    private int sharesQuantity;
    private double purchasePrice;
    private double totalValue;
    private double currentValue;

    // default no-arg constructor for when GSON deser JSON into ETF object
    public ETF() {
        this.id = UUID.randomUUID();
    }

    public ETF(UUID id, String type, String symbol, String name, int sharesQuantity, double purchasePrice,
            double totalValue,
            double currentValue) {
        this.id = id;
        this.type = type;
        this.symbol = symbol;
        this.name = name;
        this.sharesQuantity = sharesQuantity;
        this.purchasePrice = purchasePrice;
        this.totalValue = sharesQuantity * purchasePrice;
        this.currentValue = currentValue;
    }

    public ETF(String type, String symbol, String name, int sharesQuantity, double purchasePrice, double totalValue,
            double currentValue) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.symbol = symbol;
        this.name = name;
        this.sharesQuantity = sharesQuantity;
        this.purchasePrice = purchasePrice;
        this.totalValue = sharesQuantity * purchasePrice;
        this.currentValue = currentValue;
    }

    @Override
    public String toString() {
        return String.format(
                "ETF[id=%s, type=%s, symbol='%s', name='%s', sharesQuantity='%d', purchasePrice='%.2f', totalValue='%.2f', currentValue='%.2f']",
                id, type, symbol, name, sharesQuantity, purchasePrice, totalValue, currentValue);
    }

    public String getType() {
        return type;
    }

    public UUID getId() {
        return id;
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

    public void calculateTotalValue() {
        this.totalValue = this.sharesQuantity * this.purchasePrice;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

}
