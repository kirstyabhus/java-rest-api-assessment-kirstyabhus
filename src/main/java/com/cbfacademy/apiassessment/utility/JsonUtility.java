package com.cbfacademy.apiassessment.utility;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.service.PortfolioService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component
// to handle JSON serialisation and deserialisation
public class JsonUtility {

    // write JSON
    public void writePortfoliosToJSON(List<Portfolio> portfolios, String filePath) {
        // Assuming you have a List of portfolios
        // List<Portfolio> portfolios = portfolioService.getPortfolios(); // You need to
        // implement this method

        // create a Gson instance with the custom investment serialisation
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Investment.class, new InvestmentSerialiser())
                .create();

        // convert the List of portfolios to JSON
        String json = gson.toJson(portfolios);

        // write the JSON to a file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            // handle the exception
        }
    }

    // read JSON
    public List<Portfolio> readPortfoliosFromJSON(String filePath) {

        try (FileReader reader = new FileReader(filePath)) {
            // create a Gson instance with the custom investment deserialisation
            Gson gson = new GsonBuilder().registerTypeAdapter(Investment.class, new InvestmentDeserialiser())
                    .create();

            TypeToken<List<Portfolio>> portfolioListType = new TypeToken<List<Portfolio>>() {
            };

            return gson.fromJson(reader, portfolioListType);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            return null;
        }
    }
}
