package com.cbfacademy.apiassessment.model;

import java.util.Map;
import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Portfolio {

    private UUID id;
    private String name;
    private String description; // might get rid of
    private Map<String, Investment> investments;

    public Portfolio(UUID id, String name, String description, Map<String, Investment> investments) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Investment> getInvestments() {
        return investments;
    }

    // TODO Investments setter?

}
