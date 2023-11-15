package com.cbfacademy.apiassessment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.model.Stock;
import com.cbfacademy.apiassessment.repository.StockRepository;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockRepository repository;

    StockController(StockRepository repository) {
        this.repository = repository;
    }

    // get all stocks
    @GetMapping("")
    public List<Stock> all() {
        return repository.findAll();
    }

    // get stock by id
    @GetMapping("/id/{id}")
    public Stock getStockById(@PathVariable UUID id) {
        return repository.findById(id);
    }

    // get stock by name
    @GetMapping("/symbol/{symbol}")
    public Stock getStockBySymbol(@PathVariable String symbol) {
        return repository.findBySymbol(symbol);
    }

    // create a stock
    @PostMapping(path = "/new", produces = "application/json")
    public Stock createStock(@RequestBody Stock stock) {
        return repository.save(stock);
    }

    // delete a stock
    @DeleteMapping("/id/{id}")
    public List<Stock> deleteStock(@PathVariable UUID id) {
        repository.deleteStockById(id);
        return repository.findAll();
    }

}
