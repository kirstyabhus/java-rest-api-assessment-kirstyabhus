package com.cbfacademy.apiassessment.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;

@Entity
public class Portfolio {

    private UUID portfolioId;
    private String name;
    // TODO use this when ive created the totalVlaue feild
    // private Double totalValue;
    private List<Investment> investments; // check this why is it not an array

    // default no-arg constructor for when GSON deser JSON into Portfolio object
    @JsonCreator
    public Portfolio(
            @JsonProperty("portfolioId") UUID portfolioId,
            @JsonProperty("name") String name,
            @JsonProperty("investments") List<Investment> investments) {
        // if portfolioID present use it (in the case of PUT), else generate new id
        this.portfolioId = portfolioId != null ? portfolioId : UUID.randomUUID();
        this.name = name;
        this.investments = investments;
    }

    // TODO use this when ive created the totalVlaue feild
    // @JsonCreator
    // public Portfolio(
    // @JsonProperty("id") UUID portfolioId,
    // @JsonProperty("name") String name,
    // @JsonProperty("totalValue") Double totalValue,
    // @JsonProperty("investments") List<Investment> investments) {
    // this.portfolioId = portfolioId != null ? portfolioId : UUID.randomUUID();
    // this.name = name;
    // this.totalValue = totalValue;
    // this.investments = investments;
    // }

    public Portfolio(String name, List<Investment> investments) {
        this.portfolioId = UUID.randomUUID();
        this.name = name;
        this.investments = investments;
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // TODO use this when ive created the totalVlaue feild
    // public Double getTotalValue() {
    // return totalValue;
    // }

    // TODO, CHANGE THIS TO INSTEAD OF GETPURCAHSEPRICE INSTEAD TOTALVALUE FROM
    // INVESTMENTS (QUANTITY * PURCAHSE PRICE)
    // public Double calculateTotalValue() {
    // TODO this.totalValue = investments.stream().mapToDouble(Investment::)
    // }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId='" + portfolioId + '\'' +
                ", name='" + name + '\'' +
                ", investments=" + investments +
                '}';
    }

}
