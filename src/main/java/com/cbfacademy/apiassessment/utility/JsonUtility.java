package com.cbfacademy.apiassessment.utility;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cbfacademy.apiassessment.model.ESGRating;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Utility class for handling JSON serialization and deserialization.
 */
@Component
public class JsonUtility {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtility.class);

    /**
     * Writes a list of portfolios to a JSON file.
     *
     * @param portfolios The list of portfolios to be written to JSON.
     * @param filePath   The file path where the JSON will be written.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writePortfoliosToJSON(List<Portfolio> portfolios, String filePath) throws IOException {

        try {
            // create a Gson instance with the custom investment serialisation
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Investment.class, new InvestmentSerialiser())
                    .create();

            // convert the List of portfolios to JSON
            String json = gson.toJson(portfolios);

            // write the JSON to a file
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(json);
            }
        } catch (IOException e) {
            logger.error("Error writing portfolios to JSON: {}", e.getMessage(), e);
            throw new IOException("Error writing portfolios to JSON", e);
        }
    }

    /**
     * Reads a list of portfolios from a JSON file.
     *
     * @param filePath The file path from which JSON portfolios will be read.
     * @return The list of portfolios read from the JSON file.
     * @throws IOException If an I/O error occurs while reading from the file.
     */
    public List<Portfolio> readPortfoliosFromJSON(String filePath) throws IOException {

        try (FileReader reader = new FileReader(filePath)) {
            // create a Gson instance with the custom investment deserialisation
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Investment.class, new InvestmentDeserialiser());
            gsonBuilder.registerTypeAdapter(ESGRating.class, new ESGRatingDeserializer());
            Gson gson = gsonBuilder.create();

            TypeToken<List<Portfolio>> portfolioListType = new TypeToken<List<Portfolio>>() {
            };

            return gson.fromJson(reader, portfolioListType);
        } catch (IOException e) {
            logger.error("Error reading portfolios from JSON: {}", e.getMessage(), e);
            throw new IOException("Error reading portfolios from JSON", e);
        }
    }

}
