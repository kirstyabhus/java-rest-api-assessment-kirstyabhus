package com.cbfacademy.apiassessment.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbfacademy.apiassessment.model.Investment;

public interface InvestmentRepository extends JpaRepository<Investment, UUID> {
    Investment findByName(String name);
}
