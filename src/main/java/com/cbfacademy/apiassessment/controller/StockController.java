package com.cbfacademy.apiassessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.model.Stock;
import com.cbfacademy.apiassessment.repository.StockRepository;

@RestController
public class StockController {

    private final StockRepository repository;

    StockController(StockRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/stocks")
    List<Stock> all() {
        return repository.findAll();
    }

    @GetMapping("/stocks/{id}")
    Stock getStockById(@PathVariable long id) {
        return repository.findById(id);
    }
}
