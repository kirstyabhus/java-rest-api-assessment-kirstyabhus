package com.cbfacademy.apiassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;

import com.cbfacademy.apiassessment.model.Stock;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findBySymbol(String symbol);

    Stock findById(long id);
}
