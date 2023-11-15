package com.cbfacademy.apiassessment.utility;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cbfacademy.apiassessment.model.Stock;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class JsonUtility {
    // write JSON

    public void writeStocksToJSON(List<Stock> stocks, String filePath) throws IOException {

        // create a Gson instance
        Gson gson = new Gson();

        // convert the List to JSON
        String json = gson.toJson(stocks);

        // write the JSON to a file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    // read JSON
    public List<Stock> readStocksFromJSON(String filePath) throws IOException {

        try (FileReader reader = new FileReader(filePath)) {
            // create a Gson instance
            Gson gson = new Gson();

            TypeToken<List<Stock>> stockListType = new TypeToken<List<Stock>>() {
            };

            return gson.fromJson(reader, stockListType);
        }
    }
}
