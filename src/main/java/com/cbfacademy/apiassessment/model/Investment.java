package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;

/**
 * Interface representing an investment, which can be a stock or an ETF.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Stock.class, name = "Stock"),
        @JsonSubTypes.Type(value = ETF.class, name = "ETF")
})
@Entity
public interface Investment {

    String getType();

    UUID getId();

    String getSymbol();

    void setSymbol(String symbol);

    String getName();

    void setName(String name);

    ESGRating getESGRating();

    void setESGRating(ESGRating esgRating);

    int getSharesQuantity();

    void setSharesQuantity(int sharesQuantity);

    double getPurchasePrice();

    void setPurchasePrice(double purchasePrice);;

    double getTotalValue();

    void calculateTotalValue();

    double getCurrentValue();

    void setCurrentValue(double currentValue);

}
