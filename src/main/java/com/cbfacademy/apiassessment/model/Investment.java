package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public interface Investment {
    UUID getId();

    String getSymbol();

    void setSymbol(String symbol);

    String getName();

    void setName(String name);

    Double getValue();

    void setValue(Double value);

    Portfolio getPortfolio();

    void setPorfolio(Portfolio portfolio);
    // how would this work?

}
