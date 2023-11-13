package com.cbfacademy.apiassessment.model;

import java.util.Map;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Portfolio {

    private UUID id;
    private String name;
    private Map<String, Investment> investments;

    public Portfolio(UUID id, String name, Map<String, Investment> investments) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.investments = investments;
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

    public Map<String, Investment> getInvestments() {
        return investments;
    }

    // TODO Investments setter?

}
