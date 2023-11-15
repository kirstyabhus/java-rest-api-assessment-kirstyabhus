package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public interface Investment {
    // stock or etf?
    String getType();

    UUID getId();

    String getSymbol();

    void setSymbol(String symbol);

    String getName();

    void setName(String name);

    int getSharesQuantity();

    void setSharesQuantity(int sharesQuantity);

    double getPurchasePrice();

    void setPurchasePrice(double purchasePrice);;

    double getCurrentValue();

    void setCurrentValue(double currentValue);

}
