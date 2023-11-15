package com.cbfacademy.apiassessment.repository;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cbfacademy.apiassessment.model.Stock;
import com.cbfacademy.apiassessment.utility.JsonUtility;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Optional;

@Repository
public class StockRepository {

    private String filePath = "C:///Users//kabhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//stocksData.json";

    // map to store our data from json in the form ID:Stock
    private final Map<Long, Stock> stocksMap = new HashMap<>();

    @Autowired
    private JsonUtility jsonUtility;

    // populate the map with our stocks data from the JSON file "storage"
    @PostConstruct
    public void populateStockMap() {
        try {
            List<Stock> stocksList = jsonUtility.readStocksFromJSON(filePath);

            stocksList.forEach(stock -> stocksMap.put(stock.getId(), stock));
        } catch (IOException e) {
            System.out.println("Error populating stocksMap: " + e.getMessage());
        }

    }

    // get all stocks
    public List<Stock> findAll() {
        return new ArrayList<>(stocksMap.values());
    }

    // get stock by id
    public Stock findById(long id) {
        return stocksMap.get(id);
    }

    // get stock by symbol
    public Stock findBySymbol(String symbol) {
        for (Stock stock : stocksMap.values()) {
            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }

    public Stock save(Stock stock) {
        // add the investment into the map
        stocksMap.put(stock.getId(), stock);

        // update the json file
        try {
            ArrayList<Stock> stocksList = new ArrayList<>(stocksMap.values());

            jsonUtility.writeStocksToJSON(stocksList, filePath);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return stock;
    }

    /*
     * public StockRepository(JsonUtility jsonUtility){
     * this.jsonUtility = jsonUtility;
     * }
     */

    // List<Stock> findBySymbol(String symbol);

    // Stock findById(long id);
}
