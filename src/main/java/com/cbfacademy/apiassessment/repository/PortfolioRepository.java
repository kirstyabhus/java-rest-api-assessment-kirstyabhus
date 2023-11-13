package com.cbfacademy.apiassessment.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbfacademy.apiassessment.model.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
    // count by __ ? to count how many invesments in a portfolio
}
