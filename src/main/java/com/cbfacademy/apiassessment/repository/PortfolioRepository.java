package com.cbfacademy.apiassessment.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbfacademy.apiassessment.model.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
    // count by __ ? to count how many invesments in a portfolio
}
