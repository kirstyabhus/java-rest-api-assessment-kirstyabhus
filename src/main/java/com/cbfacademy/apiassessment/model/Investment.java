package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Stock.class, name = "Stock"),
        @JsonSubTypes.Type(value = ETF.class, name = "ETF")
})
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
